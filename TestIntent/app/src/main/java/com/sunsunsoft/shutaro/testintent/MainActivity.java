package com.sunsunsoft.shutaro.testintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    EditText editArgs;
    TextView textRet;
    public static final int REQUEST_CODE_1 = 1;
    public static final int REQUEST_CODE_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editArgs = (EditText)findViewById(R.id.editText1);
        textRet = (TextView)findViewById(R.id.textView1);

        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                startActivity();
                break;
            case R.id.button2:
                startFragment();
                break;
        }
    }

    /**
     * アクティビティを起動する
     */
    private void startActivity() {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra("str1", editArgs.getText().toString());
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    private void startFragment() {

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
        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {
                String ret = data.getStringExtra("str1");
                textRet.setText(ret);
            }
        }
    }
}
