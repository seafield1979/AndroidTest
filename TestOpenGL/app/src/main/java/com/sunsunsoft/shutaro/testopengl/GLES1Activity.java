package com.sunsunsoft.shutaro.testopengl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GLES1Activity extends AppCompatActivity {

    MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new MyGLView(this);
        setContentView(myGLView);
    }

    @Override
    protected void onResume(){
        super.onResume();
        myGLView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        myGLView.onPause();
    }
}
