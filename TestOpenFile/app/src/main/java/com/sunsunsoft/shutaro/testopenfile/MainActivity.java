package com.sunsunsoft.shutaro.testopenfile;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.File;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private int[] button_ids = new int[]{
            R.id.button, R.id.button2};
    private Button[] buttons = new Button[button_ids.length];
    private TextView textView;
    private FileDialog fileDialog;

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
            case R.id.button2:
                test2();
                break;
        }
    }

    /**
     * インテントを使用したファイル選択
     * 外部のアプリ(インテント)を使用するのでスマホにファイラーがインストールされていないとファイルを選択できない
     */
    private void test1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, CHOSE_FILE_CODE);
    }

    /**
     * 自前のダイアログでファイルを選択する
     */
    private void test2() {
        File mPath = new File(Environment.getExternalStorageDirectory() + "/");
        fileDialog = new FileDialog(this, mPath, null);

        if (false) {
            // ファイルを選択
            fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                public void fileSelected(File file) {
                    Log.d(getClass().getName(), "selected file " + file.toString());
                    textView.setText(file.toString());
                }
            });
        } else {
            // フォルダを選択
            fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
                public void directorySelected(File directory) {
                    Log.d(getClass().getName(), "selected dir " + directory.toString());
                    textView.setText(directory.toString());
                }
            });
            fileDialog.setSelectDirectoryOption(true);
        }
        fileDialog.showDialog();
    }
}