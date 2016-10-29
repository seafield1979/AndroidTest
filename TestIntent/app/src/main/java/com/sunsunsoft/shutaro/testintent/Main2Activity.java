package com.sunsunsoft.shutaro.testintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements OnClickListener{

    TextView text1;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text1 = (TextView)findViewById(R.id.textView);
        edit1 = (EditText)findViewById(R.id.editText);

        ((Button)findViewById(R.id.button)).setOnClickListener(this);

        // 引数を取得する
        Intent intent = getIntent();
        String str1 = intent.getStringExtra("str1");
        if (str1 != null) {
            text1.setText(str1);
        }
    }

    public void onClick(View v) {
        switch( v.getId()) {
            case R.id.button:
                // アクティビティを閉じる。呼び出し元に戻り値を返す。
            {
                Intent data = new Intent();
                data.putExtra("str1", edit1.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
                break;
        }

    }
}
