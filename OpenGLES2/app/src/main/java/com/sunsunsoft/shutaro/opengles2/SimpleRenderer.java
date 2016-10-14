package com.sunsunsoft.shutaro.opengles2;

/**
 * Created by shutaro on 2016/10/14.
 */
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import static com.sunsunsoft.shutaro.opengles2.GLES20Utils.checkGlError;

//import com.orangesignal.android.example.gles20.R;
//import com.orangesignal.android.opengl.GLES20Utils;

/**
 * ビットマップをテクスチャとして読み込んで {@link GLSurfaceView} の描画領域いっぱいに描画するだけのシンプルな {@link GLSurfaceView.Renderer} を提供します。
 *
 * @author 杉澤 浩二
 */
public final class SimpleRenderer implements GLSurfaceView.Renderer {

    TextureImage mTextureImage;
    private final Context mContext;
    private int mProgram;


    public SimpleRenderer(final Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config) {

        mTextureImage = new TextureImage(mContext);
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        // ビューポートを設定します。
        if (width > height) {
            GLES20.glViewport(0, 0, height, height);
        } else {
            GLES20.glViewport(0, 0, width, width);
        }
        checkGlError("glViewport");
    }


    @Override
    public void onDrawFrame(final GL10 gl) {
        GLES20.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mTextureImage.draw();

    }
}




// プログラムを生成して使用可能にします。
//        mProgram = GLES20Utils.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
//        if (mProgram == 0) {
//            throw new IllegalStateException();
//        }
//        GLES20.glUseProgram(mProgram);
//        checkGlError("glUseProgram");

//        int attrId = GLES20.glGetAttribLocation(mProgram,"Normal");
//        Log.v("myLog", "attrId " + attrId);
//
//        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
//        checkGlError("glGetAttribLocation aPosition");
//        if (maPositionHandle == -1) {
//            throw new RuntimeException(
//                    "Could not get attrib location for aPosition");
//        }
//        //同じように頂点シェーダのUniformであるuMVPMatrixのハンドルを取っておきます。
//        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        checkGlError("glGetUniformLocation uMVPMatrix");
//        if (muMVPMatrixHandle == -1) {
//            throw new RuntimeException(
//                    "Could not get attrib location for uMVPMatrix");
//        }

// シェーダで使用する変数のハンドルを取得し使用可能にします。

//        mPosition = GLES20.glGetAttribLocation(mProgram, "position");
//        checkGlError("glGetAttribLocation position");
//        if (mPosition == -1) {
//            throw new IllegalStateException("Could not get attrib location for position");
//        }
//        GLES20.glEnableVertexAttribArray(mPosition);

//        mTexcoord = GLES20.glGetAttribLocation(mProgram, "texcoord");
//        checkGlError("glGetAttribLocation texcoord");
//        if (mPosition == -1) {
//            throw new IllegalStateException("Could not get attrib location for texcoord");
//        }
//        GLES20.glEnableVertexAttribArray(mTexcoord);

//        mTexture = GLES20.glGetUniformLocation(mProgram, "texture");
//        checkGlError("glGetUniformLocation texture");
//        if (mTexture == -1) {
//            throw new IllegalStateException("Could not get uniform location for texture");
//        }

// テクスチャを作成します。(サーフェスが作成される度にこれを行う必要があります)
//        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.hogeman);
//        mTextureId = GLES20Utils.loadTexture(bitmap);
//        bitmap.recycle();





// 背景とのブレンド方法を設定します。
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
//        GLES20.glEnable(GLES20.GL_BLEND);
//        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	// 単純なアルファブレンド

//        // テクスチャの指定
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
//        GLES20.glUniform1i(mTexture, 0);
//        GLES20.glVertexAttribPointer(mTexcoord, 2, GLES20.GL_FLOAT, false, 0, mTexcoordBuffer);
//        GLES20.glVertexAttribPointer(mPosition, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);

//        // 回転(y軸に対し30度)
//        // モデルビュー変換行列（指定した軸を中心に回転）
//        Matrix.setRotateM(mMMatrix, 0, angleZ, 0.0f, 0.0f, 1.0f);
//        angleZ += 1.0f;

//そして視野行列とモデルビュー行列を乗算します。
// 視野行列＊モデルビュー行列
//        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
//        //最後に投影行列と乗算します。
//        // 投影行列
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
//        //できたmMVPMatrixをハンドルを使ってシェーダ内のuMVPMatrixと紐付けます（代入ですね）。
//        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);


//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

//        GLES20.glDisable(GLES20.GL_BLEND);
//        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
