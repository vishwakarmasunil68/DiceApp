package com.emobi.diceapp.twodice;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import com.emobi.diceapp.pack3.PhotoCube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sunil on 16-12-2016.
 */
public class TwoDiceRendering implements GLSurfaceView.Renderer {
    private PhotoCube cube,cube1;     // (NEW)
    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = 0; // rotational speed for cube
    private static float rotation_axis_x=0;
    private static float rotation_x=-1.0f;
    private static float rotation_y=0.0f;


    private static float rotation_x_1=1.0f;
    private static float rotation_y_1=1.0f;


    GL10 gl;
    int[] rand_faces={0, 90 ,180,270};
    public static int face_num=-1;
    static boolean x_bool=true;
    static boolean y_bool=true;
    static boolean rotation_type_vertically=true;
    // Constructor
    public TwoDiceRendering(Context context) {
        cube = new PhotoCube(context);
        cube1=new PhotoCube(context);
        // (NEW)
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // Setup Texture, each time the surface is created (NEW)
        cube.loadTexture(gl);             // Load images into textures (NEW)
        cube1.loadTexture(gl);             // Load images into textures (NEW)
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
        // Clear color and depth buffers
        drawCube(gl,cube);
        drawCube1(gl,cube1);
    }
    public void drawCube(GL10 gl,PhotoCube cube){
//        Log.d("sunil","cube1");
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // ----- Render the Cube -----
        gl.glLoadIdentity();                  // Reset the model-view matrix

        gl.glTranslatef(rotation_x_1, rotation_y_1, -6.0f);   // Translate into the screen
        if(rotation_type_vertically){
            gl.glRotatef(angleCube, angleCube, 1.0f, 1.0f); // Rotate
        }
        else{
            gl.glRotatef(angleCube, 1.0f, angleCube, 1.0f); // Rotate
        }
        cube.draw(gl);
        angleCube += speedCube;
    }
    public void drawCube1(GL10 gl,PhotoCube cube){
//        Log.d("sunil","cube2");
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // ----- Render the Cube -----
        gl.glLoadIdentity();                  // Reset the model-view matrix

        gl.glTranslatef(rotation_x, rotation_y, -6.0f);   // Translate into the screen
        if(rotation_type_vertically){
            gl.glRotatef(angleCube, angleCube, 1.0f, 1.0f); // Rotate
        }
        else{
            gl.glRotatef(angleCube, 1.0f, angleCube, 1.0f); // Rotate
        }
        cube.draw(gl);
        angleCube += speedCube;
    }
    public void finalstop(){
        speedCube=0;
        int r = (int) (Math.random() * (1000 - 0)) + 0;

        int num=r%4;
        Log.d("sunil",r+"");
        Log.d("sunil",num+"");

        if(rotation_type_vertically){
            face_num=result(num);
            angleCube=rand_faces[num];
        }
        else{
            face_num=resultnonvertically(num);
            angleCube=rand_faces[num];
        }
//        angleCube=270;
//        result();
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
        speedCube=rotationspeed;
        float x=rotation_axis;
        float y=rotation_axis;

        if(x_bool){

        }
        else{
            x=-x;
            rotation_type_vertically=false;
        }

        if(y_bool){

        }
        else{
            y=-y;
            rotation_type_vertically=true;
        }

        if(rotation_x>=1){
            x_bool=false;
        }
        if(rotation_x<=-1){
            x_bool=true;
        }

        if(rotation_y>=2.0){
            y_bool=false;
        }
        if(rotation_y<=-2.0){
            y_bool=true;
        }

        rotation_x=rotation_x+x;
        rotation_y=rotation_y+y;

    }
}