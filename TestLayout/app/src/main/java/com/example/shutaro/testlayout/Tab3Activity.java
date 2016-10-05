package com.example.shutaro.testlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Tab3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);

        // 大量のボタンを追加
        LinearLayout topView = (LinearLayout)findViewById(R.id.topLinear);

//        for (int i=0; i<100; i++) {
//            TextView tv = new TextView(this);
//            tv.setText("hoge" + String.valueOf(i+1));
//            RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.MATCH_PARENT);
//            tv.setLayoutParams(layout);
//            topView.addView(tv);
//        }
        for (int i=0; i<100; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("hoge %d", i));
            topView.addView(tv);
        }
        topView.setBackgroundColor(Color.rgb(255,0,0));
    }

}
