package com.example.shutaro.testgesture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[3];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i+1));
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
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
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
    private void test2() {
        Intent i = new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(i);
    }
    private void test3() {
    }
}