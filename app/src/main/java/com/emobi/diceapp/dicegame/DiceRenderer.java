package com.emobi.diceapp.dicegame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sunil on 15-12-2016.
 */
public class DiceRenderer implements GLSurfaceView.Renderer{
//    private PhotoCube cube;     // (NEW)
//    private static float angleCube = 0;     // rotational angle in degree for cube
//    private static float speedCube = 0; // rotational speed for cube
//    private static float rotation_axis_x=0.3f;
//    private static float rotation_x=-1.0f;
//    private static float rotation_y=0.0f;
//    private Dice cube1;
    GL10 gl;
    int[] rand_faces={0, 90 ,180,270};
//    public static int face_num=-1;
//    static boolean x_bool=true;
//    static boolean y_bool=true;
//    static boolean rotation_type_vertically=true;
    private Dice[] dices;
    boolean[] rotation_types;
    boolean[] x_bools;
    boolean[] y_bools;
    private float[] anglecubes;
    private float[] speedcubes;
    private float[] rotation_xs={-1.5f,-1.5f,0.0f,1.5f,1.5f,-1.5f,1.5f};
    private float[] rotation_ys={-2.0f,2.0f,0.0f,-2.0f,2.0f,0.0f,0.0f};
    public static float[] face_numbers={-1,-1,-1,-1,-1,-1,-1};
    int no_of_dice;
    // Constructor
    public DiceRenderer(Context context,int no_of_dice){
        this.no_of_dice=no_of_dice;
        dices=new Dice[no_of_dice];
        rotation_types=new boolean[no_of_dice];
        x_bools=new boolean[no_of_dice];
        y_bools=new boolean[no_of_dice];
        anglecubes=new float[no_of_dice];
        speedcubes=new float[no_of_dice];

        for(int i=0;i<no_of_dice;i++){
            dices[i]=new Dice(context);
            rotation_types[i]=true;
            x_bools[i]=true;
            y_bools[i]=true;
            anglecubes[i]=0;
            speedcubes[i]=0;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // Setup Texture, each time the surface is created (NEW)
//        cube.loadTexture(gl);             // Load images into textures (NEW)
        for(Dice dice:dices){
            dice.loadTexture(gl);
        }
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        this.gl=gl;

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // ----- Render the Cube -----

        // Clear color and depth buffers
        for(int i=0;i<dices.length;i++) {
            drawCube(gl, dices[i], i);
        }
    }
    public void drawCube(GL10 gl,Dice cube,int i){
        gl.glLoadIdentity();                  // Reset the model-view matrix

        gl.glTranslatef(rotation_xs[i], rotation_ys[i], -7.0f);   // Translate into the screen
        if(rotation_types[i]){
            gl.glRotatef(anglecubes[i], anglecubes[i], 1.0f, 1.0f); // Rotate
        }
        else{
            gl.glRotatef(anglecubes[i], 1.0f, anglecubes[i], 1.0f); // Rotate
        }
        cube.draw(gl);
        anglecubes[i] += speedcubes[i];
    }
    public void finalstop(){


//        while(checkDifference()){
//
//        }
        checkDifference();
//        checkDifference();
//        checkDifference();
//        checkDifference();
//        checkDifference();
//        checkDifference();
//        checkDifference();
//        checkDifference();


        for(int i=0;i<no_of_dice;i++) {
            speedcubes[i] = 0;
            int r = (int) (Math.random() * (1000 - 0)) + 0;

            int num = r % 4;
            Log.d("sunil", r + "");
            Log.d("sunil", num + "");

            if (rotation_types[i]) {
                face_numbers[i] = result(num);
                anglecubes[i] = rand_faces[num];
            } else {
                face_numbers[i] = resultnonvertically(num);
                anglecubes[i] = rand_faces[num];
            }
        }
        for(int i=0;i<7;i++){
            Log.d("rot","rotation x:"+rotation_xs[i]);
        }

        for(int i=0;i<7;i++){
            Log.d("rot","rotation y:"+rotation_ys[i]);
        }
//        rotation=rotation-1.5f;
//        rotation_axis=0.3f;
//        renderer.stopRotation(rotation,rotation_axis);

    }
    public int result(int num){
        switch (num){
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 3;
            case 3:
                return 6;
            default:
                return -1;
        }
    }
    public int resultnonvertically(int num){
        switch (num){
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return -1;
        }
    }
    public void stopRotation(float rotationspeed,float rotation_axis){
        for(int i=0;i<no_of_dice;i++) {
            speedcubes[i] = rotationspeed;
            float x = rotation_axis;
            float y = rotation_axis;

            if (x_bools[i]) {

            } else {
                x = -x;
                rotation_types[i] = false;
            }

            if (y_bools[i]) {

            } else {
                y = -y;
                rotation_types[i] = true;
            }

            if (rotation_xs[i] >= 1.4) {
                x_bools[i] = false;
            }
            if (rotation_xs[i]<= -1.4) {
                x_bools[i] = true;
            }

            if (rotation_ys[i] >= 2.0) {
                y_bools[i] = false;
            }
            if (rotation_ys[i] <= -2.0) {
                y_bools[i] = true;
            }

            rotation_xs[i] = rotation_xs[i]+ x;
            rotation_ys[i]= rotation_ys[i]+ y;
        }
    }
    public void checkDifference(){
        boolean issame=false;
        for (int i = 0; i < no_of_dice; i++)
        {
            for (int j = i + 1 ; j < no_of_dice; j++)
            {
                float diff=rotation_xs[i]-rotation_xs[j];
                if(diff<0){
                    diff=-diff;
                }
                if (diff<1.2)
                {
                    Log.d("rot","same point:-"+rotation_xs[i]+"::::"+rotation_xs[j]);

                    if((rotation_xs[i]-0.5f)<-1.7f) {
                        rotation_xs[i] = rotation_xs[i] + 0.5f;
                    }
                    else {
                        rotation_xs[i] = rotation_xs[i] - 0.5f;
                    }
                    for(int k=0;k<no_of_dice;k++){
                        float diff1=rotation_xs[i]-rotation_xs[k];
                        if(diff1<0){
                            diff1=-diff1;
                        }
                        if(diff1<1.2){

                        }
                    }
                    issame=true;
//                    if()
                    stopRotation(1,0.3f);
//                    DiceActivity.rotationmethod();
                }
            }
        }
//        for (int i = 0; i < no_of_dice; i++)
//        {
//            for (int j = i + 1 ; j < no_of_dice; j++)
//            {
//                if (rotation_ys[i]==rotation_ys[j])
//                {
//                    Log.d("rot","same point:-"+rotation_ys[i]+"::::"+rotation_ys[j]);
//                    if((rotation_ys[i]+1.0f)>2.0f) {
//                        rotation_ys[i] = rotation_ys[i] - 1.0f;
//                    }
//                    else {
//                        rotation_ys[i] = rotation_ys[i] + 1.0f;
//                    }
//                    stopRotation(10,0.3f);
//                    issame=true;
//                }
//            }
//        }

        for (int i = 0; i < no_of_dice; i++)
        {
            for (int j = i + 1 ; j < no_of_dice; j++)
            {
                float diff=rotation_ys[i]-rotation_ys[j];
                if(diff<0){
                    diff=-diff;
                }
                if (diff<1.2)
                {
                    Log.d("rot","y same diff:-"+diff);
                    Log.d("rot","same point:-"+rotation_ys[i]+"::::"+rotation_ys[j]);
                    if((rotation_ys[i]+1.0f)>2.0f) {
                        rotation_ys[i] = rotation_ys[i] - 1.0f;
                    }
                    else {
                        rotation_ys[i] = rotation_ys[i] + 1.0f;
                    }
                    stopRotation(1,0.3f);
                    issame=true;
                }
            }
        }
//        if(issame){
//            checkDifference();
//        }
    }
}