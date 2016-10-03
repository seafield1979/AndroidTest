package com.example.shutaro.testviewgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button buttonScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ボタンにイベントリスナを登録する
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);

        buttonScroll = (Button)findViewById(R.id.button_scroll);
        buttonScroll.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button1:
            {
                Intent i = new Intent(MainActivity.this, LinearLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button2:
            {
                Intent i = new Intent(MainActivity.this, TableLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button3:
            {
                Intent i = new Intent(MainActivity.this, RelativeLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button4:
            {
                Intent i = new Intent(MainActivity.this, RelativeLayout2Activity.class);
                startActivity(i);
            }
                break;
            case R.id.button5:
            {
                Intent i = new Intent(MainActivity.this, RelativeLayout3Activity.class);
                startActivity(i);
            }
                break;
            case R.id.button_scroll:
            {
                Intent i = new Intent(MainActivity.this, ScrollViewActivity.class);
                startActivity(i);
            }
                break;
        }
    }
}
