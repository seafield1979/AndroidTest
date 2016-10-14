package com.sunsunsoft.shutaro.testopengl;

/**
 * Created by shutaro on 2016/10/14.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLView extends GLSurfaceView {

    MyRenderer myRenderer;

    public MyGLView(Context context) {
        super(context);
        myRenderer = new MyRenderer();
        setRenderer(myRenderer);
    }
}