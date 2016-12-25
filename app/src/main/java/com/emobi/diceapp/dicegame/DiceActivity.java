package com.emobi.diceapp.dicegame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.emobi.diceapp.R;
import com.emobi.diceapp.pack1.ShakeDetector;
import com.emobi.diceapp.pack3.ResultImage;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class DiceActivity extends AppCompatActivity {
    private GLSurfaceView glView;                   // use GLSurfaceView
    Button btn_minus,btn_plus;
    EditText et_dices;
    TextView tv_rolling_status;
    static float rotation=30;
    static float rotation_axis=0;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    static DiceRenderer renderer;
    boolean rolling_status=false;
    int no_of_dice=1;
    MediaPlayer mediaPlayer;
    FrameLayout frame_touch;
    // Call back when the activity is started, to initialize the view

    private static PayPalConfiguration config= new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("AfDOGHD-fDAhPSxLnOM0WEnacqIeGBNcEcZaXNMitAjzV5YSCv3DQxvYrPF8QuJz76UopxQsswNcB5hD");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        glView = new GLSurfaceView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_gl);


        btn_minus= (Button) findViewById(R.id.btn_minus);
        btn_plus= (Button) findViewById(R.id.btn_plus);
        et_dices= (EditText) findViewById(R.id.et_dices);
        frame_touch= (FrameLayout) findViewById(R.id.frame_touch);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            no_of_dice=bundle.getInt("dices");
            et_dices.setText(no_of_dice+"");
        }

        //for paypal integration
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        renderer=new DiceRenderer(this,no_of_dice);
        glView= (GLSurfaceView) findViewById(R.id.glsurface);
        glView.setZOrderOnTop(true);
        glView.setBackgroundResource(R.drawable.background);
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glView.setRenderer(renderer);
        glView.getHolder().setFormat(PixelFormat.RGBA_8888);
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


        btn_minus= (Button) findViewById(R.id.btn_minus);
        tv_rolling_status= (TextView) findViewById(R.id.tv_rolling_status);

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!rolling_status)
//                    rollDice();
                String text=et_dices.getText().toString();
                int no=Integer.parseInt(text);
                if(no!=1){
                    no_of_dice=no_of_dice-1;
                    et_dices.setText(no_of_dice+"");
                    Intent intent=new Intent(DiceActivity.this,DiceActivity.class);
                    intent.putExtra("dices",no_of_dice);
                    startActivity(intent);
                    finish();
                }

            }
        });

        frame_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rolling_status)
                    rollDice();
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!rolling_status)
//                    rollDice();
                String text=et_dices.getText().toString();
                int no=Integer.parseInt(text);
                if(no!=7){
                    SharedPreferences sp=getSharedPreferences("diceapp.txt",Context.MODE_PRIVATE);
                    boolean payment=sp.getBoolean("payment",false);
                    if(payment) {
                            no_of_dice = no_of_dice + 1;
                            et_dices.setText(no_of_dice + "");
                            Intent intent = new Intent(DiceActivity.this, DiceActivity.class);
                            intent.putExtra("dices", no_of_dice);
                            startActivity(intent);
                            finish();
                        }
                    else{
                        if (no_of_dice == 2) {
                            buyMethodDialog();
                        } else {
                            no_of_dice = no_of_dice + 1;
                            et_dices.setText(no_of_dice + "");
                            Intent intent = new Intent(DiceActivity.this, DiceActivity.class);
                            intent.putExtra("dices", no_of_dice);
                            startActivity(intent);
                            finish();
                        }
                    }

                }

            }
        });


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
//                handleShakeEvent(count);
                Log.d("sunil","shaking:-"+count);
                if(!rolling_status)
                    rollDice();
            }
        });

        mediaPlayer= MediaPlayer.create(this, R.raw.shake_dice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }

    public void buyMethodDialog(){
        final Dialog dialog1 = new Dialog(DiceActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
        dialog1.setContentView(R.layout.dialog_payment);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setTitle("Payment");
        dialog1.show();
        Button ok= (Button) dialog1.findViewById(R.id.ok);
        Button cancel= (Button) dialog1.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMethod();
                dialog1.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }
    public void buyMethod(){
        PayPalPayment payment = new PayPalPayment(new BigDecimal("3"), "USD", "unlock 5 dice",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }

    public void rollDice(){
        mediaPlayer.start();
        rolling_status=true;
        tv_rolling_status.setText("Rolling");
        new CountDownTimer(5000,100){

            @Override
            public void onTick(long millisUntilFinished) {
                rotation=rotation-1.5f;
                rotation_axis=0.3f;
                renderer.stopRotation(rotation,rotation_axis);
            }

            @Override
            public void onFinish() {
                renderer.stopRotation(0,0.0f);
                renderer.finalstop();
                rotation_axis=0;
                rotation=20;
                String rollL_outcome="";
                int outcome=0;
                for(int i=0;i<no_of_dice;i++) {
                    if (DiceRenderer.face_numbers[i] != -1) {
                        if((i+1)==no_of_dice){
                            rollL_outcome+=DiceRenderer.face_numbers[i];
                        }
                        else {
                            rollL_outcome += DiceRenderer.face_numbers[i] + "+";
                        }
                        outcome+=DiceRenderer.face_numbers[i];
                    }
                }
                tv_rolling_status.setText("You Rolled : " + rollL_outcome+"="+outcome);
                final int finalOutcome = outcome;
                final String finalRollL_outcome = rollL_outcome;
                new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                            Intent intent=new Intent(DiceActivity.this,ResultImage.class);
                            intent.putExtra("img", finalRollL_outcome);
                            startActivity(intent);
                    }
                }.start();
                rolling_status=false;
            }
        }.start();
    }

    // Call back when the activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
//        glView.onPause();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
//        glView.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample","json string4:-"+ confirm.toJSONObject().toString(4));
//                    Log.d("paymentExample",confirm.toString());
//                    Log.d("paymentExample","json string:-"+confirm.toString());

                    JSONObject object=new JSONObject(confirm.toJSONObject().toString(4));
                    Log.d("paymentExample",object.toString());
                    String create_time=object.optString("create_time");
                    String id=object.optString("id");
                    String intent=object.optString("intent");
                    JSONObject response=object.optJSONObject("response");
                    String state=response.optString("state");


                    Log.d("paymentExample","state:-"+state);
                    if(state.equals("approved")){
                        SharedPreferences sp=getSharedPreferences("diceapp.txt",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putBoolean("payment",true);
                        editor.commit();
                    }
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}