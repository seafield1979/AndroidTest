package com.example.shutaro.testscroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MenuActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
                R.id.button,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6 };

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
            case R.id.button: {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button2:
            {
                Intent i = new Intent(getApplicationContext(),ScrollVActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button3:
            {
                Intent i = new Intent(getApplicationContext(),ScrollHActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button4:
                Log.v("myLog", "button4 was pushed");
                break;
            case R.id.button5:
                Log.v("myLog", "button5 was pushed");
                break;
            case R.id.button6:
                Log.v("myLog", "button6 was pushed");
                break;
        }
    }
}
