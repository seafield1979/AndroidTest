package com.example.shutaro.testnotification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "myLog";

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
    @InjectView(R.id.textView)
    TextView textView;

    public MenuActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button2:
            {
                Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button3:
                textView.append("3\n");
                break;
            case R.id.button4:
                textView.append("4\n");
                break;
            case R.id.button5:
                textView.append("5\n");
                break;
            case R.id.button6:
                textView.append("6\n");
                break;
        }
    }
}