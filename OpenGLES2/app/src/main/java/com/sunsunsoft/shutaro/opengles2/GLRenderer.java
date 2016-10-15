package com.sunsunsoft.shutaro.opengles2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.sunsunsoft.shutaro.opengles2.GLES20Utils.checkGlError;

/**
 * Created by shutaro on 2016/10/14.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    Context mContext;
    Triangle mTriangle;
    TextureImage mTextureImage;

    public GLRenderer(final Context context){
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mTriangle = new Triangle();
        mTextureImage = new TextureImage(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // ビューポートを設定します。
        if (width > height) {
            GLES20.glViewport(0, 0, height, height);
        } else {
            GLES20.glViewport(0, 0, width, width);
        }
        checkGlError("glViewport");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        //背景色(R,G,B,ALPHA)
        GLES20.glClearColor(0.0f, 1.0f, 1.0f, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // 三角形
//        mTriangle.draw();

        // テクスチャ
        mTextureImage.draw();
    }
}