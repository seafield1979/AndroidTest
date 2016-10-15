package com.sunsunsoft.shutaro.opengles2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_DONT_CARE;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glHint;
import static com.sunsunsoft.shutaro.opengles2.GLES20Utils.checkGlError;
import static javax.microedition.khronos.opengles.GL10.GL_PERSPECTIVE_CORRECTION_HINT;

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
    private static final float POS_X = 0.5f;
    private static final float POS_Y = 0;
    private static final float VERTEXS[] = {
            -SCALE + POS_X,  SCALE + POS_Y, 0.0f,	// 左上
            -SCALE + POS_X, -SCALE + POS_Y, 0.0f,	// 左下
            SCALE + POS_X,  SCALE + POS_Y, 0.0f,	// 右上
            SCALE + POS_X, -SCALE + POS_Y, 0.0f	// 右下
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

        // テクスチャを作成します。(サーフェスが作成される度にこれを行う必要があります)
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.hogeman);
        mTextureId = GLES20Utils.loadTexture(bitmap);
        bitmap.recycle();

        mTexture = GLES20.glGetUniformLocation(mProgram, "texture");
        mTexcoord = GLES20.glGetAttribLocation(mProgram, "texcoord");
        mPosition = GLES20.glGetAttribLocation(mProgram, "position");

    }

    public void draw(){
        GLES20.glUseProgram(mProgram);
        GLES20Utils.checkGlError("glUseProgram");

        GLES20.glEnableVertexAttribArray(mTexcoord);
        GLES20.glEnableVertexAttribArray(mPosition);

        // 以下のコードを実行するとGLES20.glGetError()が1280エラー(GL_INVALID_ENUM)を返すのでコメントアウト
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        glEnable(GLES20.GL_BLEND);

        // 単純なアルファブレンド
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // テクスチャの指定
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(mTexture, 0);
        GLES20.glVertexAttribPointer(mTexcoord, 2, GLES20.GL_FLOAT, false, 0, mTexcoordBuffer);
        GLES20.glVertexAttribPointer(mPosition, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEXS.length / 3);

        GLES20.glDisable(GLES20.GL_BLEND);
//        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
    }
}
