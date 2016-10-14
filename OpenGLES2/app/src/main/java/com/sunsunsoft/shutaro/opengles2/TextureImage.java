package com.sunsunsoft.shutaro.opengles2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

import static com.sunsunsoft.shutaro.opengles2.GLES20Utils.checkGlError;

/**
 * Created by shutaro on 2016/10/14.
 */

public class TextureImage {

    private Context mContext;
    private int mProgram;
    private int mTextureId;
    private int mTexture;
    private int mTexcoord;
    private int mPosition;

    /**
     * 頂点データです。
     */
    private static final float SCALE = 0.5f;
    private static final float VERTEXS[] = {
            -SCALE,  SCALE, 0.0f,	// 左上
            -SCALE, -SCALE, 0.0f,	// 左下
            SCALE,  SCALE, 0.0f,	// 右上
            SCALE, -SCALE, 0.0f	// 右下
    };

    /**
     * テクスチャ (UV マッピング) データです。
     */
    private static final float TEXCOORDS[] = {
            0.0f, 0.0f,	// 左上
            0.0f, 1.0f,	// 左下
            1.0f, 0.0f,	// 右上
            1.0f, 1.0f	// 右下
    };
    /**
     * ポリゴン描画用のバーテックスシェーダ (頂点シェーダ) のソースコード
     */
    private static final String VERTEX_SHADER =
            "attribute vec4 position;" +
                    "attribute vec2 texcoord;" +
                    "varying vec2 texcoordVarying;" +
                    "void main() {" +
                    "gl_Position = position;" +
                    "texcoordVarying = texcoord;" +
                    "}";

    /**
     * 色描画用のピクセル/フラグメントシェーダのソースコード
     */
    private static final String FRAGMENT_SHADER =
            "precision mediump float;" +
                    "varying vec2 texcoordVarying;" +
                    "uniform sampler2D texture;" +
                    "void main() {" +
                    "gl_FragColor = texture2D(texture, texcoordVarying);" +
                    "}";

    /**
     * 頂点バッファを保持します。
     */
    private final FloatBuffer mVertexBuffer   = GLES20Utils.createBuffer(VERTEXS);

    /**
     * テクスチャ (UV マッピング) バッファを保持します。
     */
    private final FloatBuffer mTexcoordBuffer = GLES20Utils.createBuffer(TEXCOORDS);

    public TextureImage(Context context) {
        mContext = context;

        // プログラムを生成して使用可能にします。
        mProgram = GLES20Utils.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        if (mProgram == 0) {
            throw new IllegalStateException();
        }
        GLES20.glUseProgram(mProgram);
        GLES20Utils.checkGlError("glUseProgram");

        // テクスチャを作成します。(サーフェスが作成される度にこれを行う必要があります)
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.hogeman);
        mTextureId = GLES20Utils.loadTexture(bitmap);
        bitmap.recycle();

        mTexture = GLES20.glGetUniformLocation(mProgram, "texture");
        checkGlError("glGetUniformLocation texture");
        if (mTexture == -1) {
            throw new IllegalStateException("Could not get uniform location for texture");
        }

        mTexcoord = GLES20.glGetAttribLocation(mProgram, "texcoord");
        checkGlError("glGetAttribLocation texcoord");
        if (mTexcoord == -1) {
            throw new IllegalStateException("Could not get attrib location for texcoord");
        }
        GLES20.glEnableVertexAttribArray(mTexcoord);

        mPosition = GLES20.glGetAttribLocation(mProgram, "position");
        checkGlError("glGetAttribLocation position");
        if (mPosition == -1) {
            throw new IllegalStateException("Could not get attrib location for position");
        }
        GLES20.glEnableVertexAttribArray(mPosition);

    }

    public void draw(){
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	// 単純なアルファブレンド

        // テクスチャの指定
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(mTexture, 0);
        GLES20.glVertexAttribPointer(mTexcoord, 2, GLES20.GL_FLOAT, false, 0, mTexcoordBuffer);
        GLES20.glVertexAttribPointer(mPosition, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
    }
}
