package com.example.shutaro.testactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private EditText edit1;
    private EditText edit2;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Resourcesクラスを使ってリソースを参照する
        Resources res = getResources();
        String titleString = res.getString(R.string.hoge_text);
        Log.v("myLog",titleString);

        button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(this);

        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

        edit1 = (EditText)findViewById(R.id.editText);
        edit2 = (EditText)findViewById(R.id.editText2);
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.button: {
                // Main2Activity アクティビティを呼び出す
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("str1", edit1.getText().toString());
                try {
                    intent.putExtra("int1", Integer.parseInt(edit2.getText().toString()));
                } catch (NumberFormatException e) {
                    Log.e("error", e.getMessage());
                    toastMake(e.getMessage(), 0, 0);
                    break;
                }
                startActivityForResult(intent, REQUEST_CODE);
            }
                break;
            case R.id.button2: {
                // メーラーアクティビティ
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
                break;
            case 2:
                break;
        }
    }


    /**
     * 他のActivityの戻り値を取得する
     * @param requestCode
     * @param resultCode
     * @param data
     */
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


    // Toast を表示する
    // x,y はデフォルトの表示位置(画面中央)からのオフセット
    private void toastMake(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, x, y);
        toast.show();
    }
}
