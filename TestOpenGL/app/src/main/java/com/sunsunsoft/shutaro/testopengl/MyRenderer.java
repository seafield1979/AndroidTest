package com.sunsunsoft.shutaro.testopengl;

/**
 * Created by shutaro on 2016/10/14.
 */

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyRenderer implements Renderer {

    MyCube cube = new MyCube();
    private float angleY = 30.f;

    // 生成時
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        // 光源
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);

    }

    // 描画処理
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);

        // モデル描画用のパラメータ設定
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // 移動 zに-3(奥に移動)
        gl.glTranslatef(0, 0, -3f);

        // 回転(y軸に対し30度)
        gl.glRotatef(angleY, 0, 1, 0);
        angleY += 0.5f;

        cube.draw(gl);
    }


    // サイズ変更(端末の向きが変更された場合など)
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45f,(float) width / height, 1f, 50f);
    }


}