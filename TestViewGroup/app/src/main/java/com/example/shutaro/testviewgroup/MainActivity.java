package com.example.shutaro.testviewgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ボタンにイベントリスナを登録する
        findViewById(R.id.button_linear1).setOnClickListener(this);
        findViewById(R.id.button_linear2).setOnClickListener(this);
        findViewById(R.id.button_table).setOnClickListener(this);
        findViewById(R.id.button_relative).setOnClickListener(this);
        findViewById(R.id.button_relative2).setOnClickListener(this);
        findViewById(R.id.button_relative3).setOnClickListener(this);
        findViewById(R.id.button_scroll).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_linear1:
            {
                Intent i = new Intent(MainActivity.this, LinearLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button_linear2:
            {
                Intent i = new Intent(MainActivity.this, LinearLayoutActivity2.class);
                startActivity(i);
            }
                break;
            case R.id.button_table:
            {
                Intent i = new Intent(MainActivity.this, TableLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button_relative:
            {
                Intent i = new Intent(MainActivity.this, RelativeLayoutActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button_relative2:
            {
                Intent i = new Intent(MainActivity.this, RelativeLayout2Activity.class);
                startActivity(i);
            }
                break;
            case R.id.button_relative3:
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
