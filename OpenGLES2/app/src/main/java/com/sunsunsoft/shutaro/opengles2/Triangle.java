package com.sunsunsoft.shutaro.opengles2;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by shutaro on 2016/10/14.
 */

public class Triangle {
    //シンプルなシェーダー
    public final String vertexShaderCode =
            "attribute  vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}";
    //シンプル色は自分で指定(R,G,B ALPHA)指定
    public final String fragmentShaderCode =
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor =vec4(1.0, 0.0, 0.0, 1.0);" +
            "}";
    private int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    private  int mProgram;

    public Triangle(){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(){
        GLES20.glUseProgram(mProgram);
        int positionAttrib = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionAttrib);

        float vertices[] = {
                0.0f, 0.5f, 0.0f,//三角形の点A(x,y,z)
                -0.5f, -0.5f, 0.0f,//三角形の点B(x,y,z)
                0.5f, -0.5f, 0.0f//三角形の点C(x,y,z)
        };


        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);


        GLES20.glVertexAttribPointer(positionAttrib, 3, GLES20.GL_FLOAT, false, 3 *4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length/3);

        GLES20.glDisableVertexAttribArray(positionAttrib);
    }
}