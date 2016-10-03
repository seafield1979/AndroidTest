package com.example.shutaro.testscroll;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScrollHActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_h);

        LinearLayout linear1 = (LinearLayout)findViewById(R.id.linearMain);

        for (int i=0; i<100; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("hoge %d", i));
            if (i%2 == 0) {
                tv.setBackgroundColor(Color.rgb(200,100,0));
            }

            tv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);

            linear1.addView(tv);
        }
    }
}
