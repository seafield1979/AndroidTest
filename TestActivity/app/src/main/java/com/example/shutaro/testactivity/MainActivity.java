package com.example.shutaro.testactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button button1;
    private Button button2;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Resourcesクラスを使ってリソースを参照する
        Resources res = getResources();
        String titleString = res.getString(R.string.hoge_text);
        Log.v("myLog",titleString);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(R.string.hoge_text);
        textView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color
                .colorPrimary));

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button1) {
            // Main2Activity アクティビティを呼び出す
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("str1", "Hoge!");
            intent.putExtra("int1", (Integer)100);
            startActivityForResult(intent, REQUEST_CODE);

        } else if (v == button2) {
            // 指定のアクションを持つアクティビティを起動する(複数ある場合はユーザーが選択できる)
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);

            //宛先をセット
            intent.setData(Uri.parse("mailto:hogehogew@gmail.com"));

            //標題をセット
            intent.putExtra(Intent.EXTRA_SUBJECT, "XXX");

            //本文をセット
            intent.putExtra(Intent.EXTRA_TEXT, "本文");
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String value = data.getStringExtra("key_name");
                TextView result = (TextView) findViewById(R.id.textViewResult);
                result.setText(value);
            }
        }
    }
}
