package com.sunsunsoft.shutaro.opengles2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shutaro on 2016/10/14.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    Context mContext;

    public GLRenderer(final Context context){
        mContext = context;
    }

    Triangle triangle;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangle = new Triangle();
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }
    @Override
    public void onDrawFrame(GL10 unused) {
        //背景色(R,G,B,ALPHA)
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        triangle.draw();
    }
}