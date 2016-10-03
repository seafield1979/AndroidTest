package com.example.shutaro.testimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout linear1 = (LinearLayout)findViewById(R.id.scroll_h);
        LinearLayout linear2 = (LinearLayout)findViewById(R.id.scroll_v);

        // 大量のViewを追加する
        for (int i=0; i<100; i++) {
            TextView tv = new TextView(this);
            tv.setWidth(100);
            tv.setHeight(100);
            tv.setText("hoge" + String.valueOf(i+1));
            linear1.addView(tv);
        }

        for (int i=0; i<100; i++) {
            TextView tv = new TextView(this);
            tv.setWidth(100);
            tv.setHeight(100);
            tv.setText("hoge" + String.valueOf(i+1));
            linear2.addView(tv);
        }
    }
}
