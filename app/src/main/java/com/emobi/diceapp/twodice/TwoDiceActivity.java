package com.emobi.diceapp.twodice;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.emobi.diceapp.R;
import com.emobi.diceapp.pack1.ShakeDetector;
import com.emobi.diceapp.pack3.ResultImage;

public class TwoDiceActivity extends AppCompatActivity {
    private GLSurfaceView glView;                   // use GLSurfaceView
//    Button btn_rotate;
    TextView tv_rolling_status;
    float rotation=20;
    float rotation_axis=0;
    float x_axis_rotation=0.0f;
    float y_axid_rotation=0.0f;


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    TwoDiceRendering renderer;
    boolean rolling_status=false;

    // Call back when the activity is started, to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        glView = new GLSurfaceView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_gl);

        glView= (GLSurfaceView) findViewById(R.id.glsurface);
        renderer=new TwoDiceRendering(this);
        glView.setRenderer(renderer);
//        btn_rotate= (Button) findViewById(R.id.btn_rotate);
        tv_rolling_status= (TextView) findViewById(R.id.tv_rolling_status);

//        btn_rotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!rolling_status)
//                    rollDice();
//            }
//        });


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

    }

    public void rollDice(){
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
                if(TwoDiceRendering.face_num!=-1){
                    tv_rolling_status.setText("You Rolled : "+TwoDiceRendering.face_num);
                    new CountDownTimer(2000,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            Intent intent=new Intent(TwoDiceActivity.this,ResultImage.class);
                            intent.putExtra("img",TwoDiceRendering.face_num);
                            startActivity(intent);
                        }
                    }.start();

                }
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
}