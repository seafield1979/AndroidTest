package com.example.shutaro.testtextview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button1) {
            Intent i = new Intent(MainActivity.this, TestStyleActivity.class);
            startActivity(i);
        } else if (v == button2) {
            Intent i = new Intent(MainActivity.this, TestGravityActivity.class);
            startActivity(i);
        }
    }
}
