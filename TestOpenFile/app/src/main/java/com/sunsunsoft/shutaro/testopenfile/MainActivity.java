package com.sunsunsoft.shutaro.testopenfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private int[] button_ids = new int[]{
            R.id.button};
    private Button[] buttons = new Button[button_ids.length];
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        textView = (TextView) findViewById(R.id.textView);
    }


    // 識別用のコード
    public static final int CHOSE_FILE_CODE = 12345;

    /**
     * ファイルが選択されると呼ばれるコールバックメソッド
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CHOSE_FILE_CODE && resultCode == RESULT_OK) {
                String filePath = data.getDataString().replace("file://", "");
                String decodedfilePath = URLDecoder.decode(filePath, "utf-8");
                textView.setText(decodedfilePath);
            }
        } catch (Exception e) {// (UnsupportedEncodingException e) {
            // いい感じに例外処理
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                test1();
                break;
        }
    }

    private void test1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, CHOSE_FILE_CODE);
    }
}