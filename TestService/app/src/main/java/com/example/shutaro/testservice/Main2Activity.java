package com.example.shutaro.testservice;

/**
 * Serviceクラスのテスト用Activity
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements OnClickListener {
    TextView mTextView;

    private Button[] buttons = new Button[3];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i + 1));
        }

        mTextView = (TextView)findViewById(R.id.textView2);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
        }
    }

    private void test1() {
        mTextView.append("Start Service\n");
        startService(new Intent(getBaseContext(), MyService.class));
    }

    private void test2() {
        mTextView.append("Stop Service\n");
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    private void test3() {

    }

}