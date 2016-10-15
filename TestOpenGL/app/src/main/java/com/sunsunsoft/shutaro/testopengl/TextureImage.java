package com.sunsunsoft.shutaro.testopengl;

/**
 * Created by shutaro on 2016/10/14.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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
        GLES20.glUseProgram(mProgram);
        GLES20Utils.checkGlError("glUseProgram");

        // テクスチャを作成します。(サーフェスが作成される度にこれを行う必要があります)
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.hogeman);
        mTextureId = GLES20Utils.loadTexture(bitmap);
        bitmap.recycle();

        mTexture = GLES20.glGetUniformLocation(mProgram, "texture");
        GLES20Utils.checkGlError("glGetUniformLocation texture");
        if (mTexture == -1) {
            throw new IllegalStateException("Could not get uniform location for texture");
        }

        mTexcoord = GLES20.glGetAttribLocation(mProgram, "texcoord");
        GLES20Utils.checkGlError("glGetAttribLocation texcoord");
        if (mTexcoord == -1) {
            throw new IllegalStateException("Could not get attrib location for texcoord");
        }
        GLES20.glEnableVertexAttribArray(mTexcoord);

        mPosition = GLES20.glGetAttribLocation(mProgram, "position");
        GLES20Utils.checkGlError("glGetAttribLocation position");
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

class GLES20Utils {

    /**
     * ログ出力用のタグです。
     */
    private static String TAG = "GLES20Utils";

    /**
     * オブジェクトが無効であることを表します。<p>
     *
     * @see {@link #createProgram(String, String)}
     * @see {@link #loadShader(int, String)}
     */
    public static final int INVALID = 0;

    /**
     * インスタンス化できない事を強制します。
     */
    private GLES20Utils() {}

    /**
     * 最初の要素の位置です。
     */
    private static final int FIRST_INDEX = 0;

    private static final int DEFAULT_OFFSET = 0;

    private static final int FLOAT_SIZE_BYTES = 4;

    /**
     * 指定されたプリミティブ型配列のデータを {@link FloatBuffer} へ変換して返します。
     *
     * @param array バッファデータ
     * @return 変換されたバッファデータ
     * @see {@link GLES20#glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer)}
     */
    public static FloatBuffer createBuffer(float[] array) {
        final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(array).position(FIRST_INDEX);
        return buffer;
    }

    /**
     * 指定されたバーテックスシェーダとフラグメントシェーダを使用してプログラムを生成します。
     *
     * @param vertexSource ポリゴン描画用バーテックスシェーダのソースコード
     * @param fragmentSource 色描画用のフラグメントシェーダのソースコード
     * @return プログラムハンドラまたは {@link #INVALID}
     * @throws GLException OpenGL API の操作に失敗した場合
     */
    public static int createProgram(final String vertexSource, final String fragmentSource) throws GLException {
        // バーテックスシェーダをコンパイルします。
        final int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == INVALID) {
            return INVALID;
        }

        // フラグメントシェーダをコンパイルします。
        final int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == INVALID) {
            return INVALID;
        }

        // プログラムを生成して、プログラムへバーテックスシェーダとフラグメントシェーダを関連付けます。
        int program = GLES20.glCreateProgram();
        if (program != INVALID) {
            // プログラムへバーテックスシェーダを関連付けます。
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            // プログラムへフラグメントシェーダを関連付けます。
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");

            GLES20.glLinkProgram(program);
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, DEFAULT_OFFSET);
            if (linkStatus[FIRST_INDEX] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = INVALID;
            }
        }

        return program;
    }

    /**
     * 指定されたシェーダのソースコードをコンパイルします。
     *
     * @param shaderType シェーダの種類
     * @param source シェーダのソースコード
     * @return シェーダハンドラまたは {@link #INVALID}
     * @see {@link GLES20#GL_VERTEX_SHADER}
     * @see {@link GLES20.GL_FRAGMENT_SHADER}
     */
    public static int loadShader(final int shaderType, final String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != INVALID) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            final int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, DEFAULT_OFFSET);
            if (compiled[FIRST_INDEX] == INVALID) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = INVALID;
            }
        }
        return shader;
    }

    /**
     * 指定された直前の OpenGL API 操作についてエラーが発生しているかどうか検証します。
     *
     * @param op 検証する直前に操作した OpenGL API 名
     * @throws GLException 直前の OpenGL API 操作でエラーが発生している場合
     */
    public static void checkGlError(final String op) throws GLException {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new GLException(error, op + ": glError " + error);
        }
    }

    /**
     * 指定された {@link Bitmap} 情報をテクスチャへ紐付けます。
     *
     * @param bitmap テクスチャへ紐付ける {@link Bitmap} 情報
     * @return テクスチャ ID
     */
    public static int loadTexture(final Bitmap bitmap) {
        return loadTexture(bitmap, GLES20.GL_NEAREST, GLES20.GL_LINEAR);
    }

    /**
     * 指定された {@link Bitmap} 情報をテクスチャへ紐付けます。<p>
     * この実装は簡易なテクスチャの初期化のみで繰り返しの指定をサポートしません。
     *
     * @param bitmap テクスチャへ紐付ける {@link Bitmap} 情報
     * @param min テクスチャを縮小するときの補完方法
     * @param mag テクスチャを拡大するときの補完方法
     * @return テクスチャ ID
     * @see {@link GLES20#GL_TEXTURE_MIN_FILTER}
     * @see {@link GLES20#GL_TEXTURE_MAG_FILTER}
     */
    public static int loadTexture(final Bitmap bitmap, final int min, final int mag) {
        final int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, DEFAULT_OFFSET);

        final int texture = textures[FIRST_INDEX];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // テクスチャを拡大/縮小する方法を設定します。
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, min);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, mag);

        return texture;
    }

}