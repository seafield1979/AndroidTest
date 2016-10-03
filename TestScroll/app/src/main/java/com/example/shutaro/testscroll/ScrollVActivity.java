package com.example.shutaro.testscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScrollVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_v);

        LinearLayout linear1 = (LinearLayout)findViewById(R.id.linearMain);

        for (int i=0; i<100; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("hoge %d", i));
            linear1.addView(tv);
        }
    }
}
