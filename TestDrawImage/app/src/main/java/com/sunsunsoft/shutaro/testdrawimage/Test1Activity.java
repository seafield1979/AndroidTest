package com.sunsunsoft.shutaro.testdrawimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Test1Activity extends AppCompatActivity {

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    MySurfaceView1 surface1;
    MySurfaceView2 surface2;
    MySurfaceView3 surface3;
    SurfaceView currentSurface;
    LinearLayout surfaceContainer;      // SurfaceViewの表示先のView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.inject(this);

        surfaceContainer = (LinearLayout)findViewById(R.id.surface_container);

        surface1 = new MySurfaceView1(this);
        surfaceContainer.addView(surface1);
        currentSurface = surface1;

        surface2 = new MySurfaceView2(this);
        surface3 = new MySurfaceView3(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    void test1() {
        if (currentSurface != surface1) {
            surfaceContainer.removeView(currentSurface);
            surfaceContainer.addView(surface1);
            currentSurface = surface1;
        }
    }
    void test2() {
        if (currentSurface != surface2) {
            surfaceContainer.removeView(currentSurface);
            surfaceContainer.addView(surface2);
            currentSurface = surface2;
        }
    }
    void test3() {
        if (currentSurface != surface3) {
            surfaceContainer.removeView(currentSurface);
            surfaceContainer.addView(surface3);
            currentSurface = surface3;
        }
    }
    void test4() {

    }
    void test5() {

    }
    void test6() {

    }
}
