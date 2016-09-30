package com.example.shutaro.test1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // ボタンを押した時の処理を追加
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                Log.v("Button", "1");
            }
        });

        Button buttonFinish = (Button)findViewById(R.id.button_finish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                finish();
            }
        });
    }

}
