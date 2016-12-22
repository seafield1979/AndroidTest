package com.example.shutaro.testviewgroup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LinearLayoutActivity2 extends AppCompatActivity implements OnClickListener{

    private LinearLayout layout1;
    private LinearLayout layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout2);

        // Layout
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);

        // Set Event Listener
        (findViewById(R.id.button1)).setOnClickListener(this);
        (findViewById(R.id.button2)).setOnClickListener(this);
        (findViewById(R.id.button3)).setOnClickListener(this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button1:
                // 直接サイズを設定
                layout1.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                break;
            case R.id.button2:
                // 高さだけ0にする
                layout1.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0));
                break;
            case R.id.button3:
                // 非表示にする
                layout1.setVisibility(View.GONE);
                break;
        }
    }
}
