package com.emobi.diceapp.openglcode;

import android.graphics.Bitmap;

import com.emobi.diceapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sunil on 13-12-2016.
 */
public class Cube {
    private FloatBuffer mVertexBuffer;
    private FloatBuffer texBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;
    private int numFaces=6;
    private int[] imagFileIDs={
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };
    private int[] textureIDs=new int[numFaces];
    private Bitmap[] bitmaps=new Bitmap[numFaces];
    private float cubeHalfSize=1.2f;


    private float vertices[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f
    };
    private float colors[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.5f, 0.0f, 1.0f,
            1.0f, 0.5f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };

    private byte indices[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };

    public Cube() {
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuf.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
//
//        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
//        mIndexBuffer.put(indices);
//        mIndexBuffer.position(0);
//        ByteBuffer vbb = ByteBuffer.allocateDirect(12*4*numFaces);
//        vbb.order(ByteOrder.nativeOrder());
//        mVertexBuffer = vbb.asFloatBuffer();
//
//        for(int face=0;face<numFaces;face++){
//            bitmaps[face]= BitmapFactory.decodeStream(context.getResources().openRawResource(imagFileIDs[face]));
//            int width=bitmaps[face].getWidth();
//            int height=bitmaps[face].getHeight();
//
//            float facewidth=2.0f;
//            float faceheight=2.0f;
//
//            if(width>height){
//                faceheight=faceheight*height/width;
//            }
//            else{
//                facewidth=facewidth*width/height;
//            }
//            float faceLeft=-facewidth/2;
//            float faceRight=-faceLeft;
//            float faceTop=faceheight/2;
//            float faceBottom=-faceTop;
//
//            float[] vertices={
//                    faceLeft,faceBottom,0.0f, //0. left bottom front
//                    faceRight,faceBottom,0.0f,//1. right bottom front
//                    faceLeft,faceTop,0.0f,    //2. left top front
//                    faceRight,faceTop,0.0f    //3. right-top-front
//            };
//            mVertexBuffer.put(vertices);
//        }
//        mVertexBuffer.position(0);
//
//        float[] textcords={
//                0.0f,1.0f,
//                1.0f,1.0f,
//                0.0f,0.0f,
//                1.0f,0.0f
//        };
//
//        ByteBuffer tbb=ByteBuffer.allocateDirect(textcords.length*4*numFaces);
//        tbb.order(ByteOrder.nativeOrder());
//        texBuffer=tbb.asFloatBuffer();
//        for(int face=0;face<numFaces;face++){
//            texBuffer.put(textcords);
//        }
//        texBuffer.position(0);
//        mVertexBuffer.put(vertices);
//        mVertexBuffer.position(0);
//
//        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
//        byteBuf.order(ByteOrder.nativeOrder());
//        mColorBuffer = byteBuf.asFloatBuffer();
//        mColorBuffer.put(colors);
//        mColorBuffer.position(0);
//
//        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
//        mIndexBuffer.put(indices);
//        mIndexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE,
                mIndexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
//    public void draw(GL10 gl){
//        gl.glFrontFace(GL10.GL_CW);
//
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
//        gl.glColorPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//
//        //front
//        gl.glPushMatrix();
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[0]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
//        gl.glPopMatrix();
//
//
//        //left
//        gl.glPushMatrix();
//        gl.glRotatef(270.0f,0f,1f,0f);
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[1]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,4,4);
//        gl.glPopMatrix();
//
//
//        //back
//        gl.glPushMatrix();
//        gl.glRotatef(180.0f,0f,1f,0f);
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[2]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,4,4);
//        gl.glPopMatrix();
//
//        //right
//        gl.glPushMatrix();
//        gl.glRotatef(90.0f,0f,1f,0f);
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[3]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,12,4);
//        gl.glPopMatrix();
//
//
//        //top
//        gl.glPushMatrix();
//        gl.glRotatef(270.0f,0f,1f,0f);
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[4]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,16,4);
//        gl.glPopMatrix();
//
//
//        //bottom
//        gl.glPushMatrix();
//        gl.glRotatef(180.0f,0f,1f,0f);
//        gl.glTranslatef(0f,0f,cubeHalfSize);
//        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[5]);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,20,4);
//        gl.glPopMatrix();
//
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//    }
}
