package com.example.shutaro.testtextview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TestStyleActivity extends AppCompatActivity {

    private TextView[] textViews = new TextView[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_style);

        textViews[0] = (TextView)findViewById(R.id.textView);
        textViews[1] = (TextView)findViewById(R.id.textView2);
        textViews[2] = (TextView)findViewById(R.id.textView3);
        textViews[3] = (TextView)findViewById(R.id.textView4);
        textViews[4] = (TextView)findViewById(R.id.textView5);
        textViews[5] = (TextView)findViewById(R.id.textView6);
        textViews[6] = (TextView)findViewById(R.id.textView7);
        textViews[7] = (TextView)findViewById(R.id.textView8);
        textViews[8] = (TextView)findViewById(R.id.textView9);
        textViews[9] = (TextView)findViewById(R.id.textView10);
        test1();
    }

    public void test1() {
        // テキストの色
        textViews[0].setTextColor(Color.rgb(0,100,0));
        // 背景色
        textViews[1].setBackgroundColor(Color.rgb(255,0,0));
        // テキストのサイズ
        textViews[2].setTextSize(30.0f);

        // 影
        // setShadowLayer(radius ぼやけ具合, x 文字からの移動距離, y 文字からの移動距離, color 影の色);
        textViews[3].setTextSize(30.0f);
        textViews[3].setShadowLayer(1.5f,3.0f,3.0f,Color.rgb(0,0,255));

        // 画像を表示
        Drawable image = ResourcesCompat.getDrawable(getResources(), R.drawable.robot, null);
        // 画像サイズを指定
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        textViews[4].setWidth(500);
        textViews[4].setHeight(200);
        textViews[4].setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textViews[4].setBackgroundColor(Color.rgb(200,200,200));
        textViews[4].setCompoundDrawables(image, null, null, null);

        // スタイル
        textViews[5].setTypeface(Typeface.MONOSPACE,Typeface.ITALIC | Typeface.BOLD);
    }
}
