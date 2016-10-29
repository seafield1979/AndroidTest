package com.example.shutaro.testactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Main2Activity extends AppCompatActivity {

    private TextView textViewStr1;
    private TextView textViewInt1;
    private EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewStr1 = (TextView) findViewById(R.id.textViewStr1);
        textViewInt1 = (TextView) findViewById(R.id.textViewInt1);
        edit1 = (EditText) findViewById(R.id.editText);

        // 引数を取得する
        Intent intent = getIntent();
        String str1 = intent.getStringExtra("str1");
        if (str1 != null) {
            textViewStr1.setText(str1);
        }
        Integer int1 = intent.getIntExtra("int1", 0);
        textViewInt1.setText(String.valueOf(int1));

        findViewById(R.id.backActivity).setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra("key_name", edit1.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        );
    }


}
