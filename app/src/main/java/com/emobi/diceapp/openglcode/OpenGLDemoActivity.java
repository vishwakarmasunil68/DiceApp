package com.emobi.diceapp.openglcode;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.emobi.diceapp.R;

public class OpenGLDemoActivity extends AppCompatActivity {
//    Button btn_rotate;
    GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        final OpenGLRenderer openGLRenderer=new OpenGLRenderer();
//        GLSurfaceView view = new GLSurfaceView(this);
//        view.setRenderer(openGLRenderer);
        setContentView(R.layout.activity_my_gl);
        glSurfaceView= (GLSurfaceView) findViewById(R.id.glsurface);
        final OpenGLRenderer openGLRenderer=new OpenGLRenderer();
        glSurfaceView.setRenderer(openGLRenderer);
//        btn_rotate= (Button) findViewById(R.id.btn_rotate);
//
//        btn_rotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CountDownTimer(3000, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//
//                    }
//
//                    public void onFinish() {
//                        Log.d("sunil", "calling finish");
//                        openGLRenderer.stopRotation();
//                    }
//
//                }.start();
//            }
//        });

//        new CountDownTimer(3000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            public void onFinish() {
//                Log.d("sunil","calling finish");
//                openGLRenderer.stopRotation();
//            }
//
//        }.start();
    }

}
