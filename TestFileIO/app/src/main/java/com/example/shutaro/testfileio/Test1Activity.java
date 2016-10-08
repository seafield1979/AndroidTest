package com.example.shutaro.testfileio;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class Test1Activity extends AppCompatActivity implements OnClickListener {

    private Button[] buttons = new Button[3];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3 };

    private TextView textView;
    private String readText;
    private static final int CODE_PICKFILE_RESULT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i + 1));
        }

        textView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
        }
    }

    private void test1() {
        String str = writeContents("hoge");
        Log.d("myLog", str);
        textView.setText(str);

    }
    private void test2() {
        // ファイルパスを取得するアクティビティを起動
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("file/*");
//        startActivityForResult(intent, CODE_PICKFILE_RESULT);
        readFile("/storage/emulated/0/temp/test.txt");
    }
    private void test3() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_PICKFILE_RESULT) {
                Uri uri = data.getData();
                readFile(uri.getPath());
            }
        }
    }

    /**
     * ファイルに保存する
     * @param contents
     * @return
     */
    private String writeContents(String contents) {
        File temppath = new File(Environment.getExternalStorageDirectory(),"temp");
//        File temppath = new File(Environment.getDownloadCacheDirectory(),"temp");
//        File temppath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"temp");
//        File temppath = new File(Environment.getRootDirectory(),"temp");

        if (temppath.exists() != true) {
            temppath.mkdirs();
        }

        File tempfile = new File(temppath, "test.txt");
        FileWriter output = null;
        try {
            output = new FileWriter(tempfile, true);
            output.write(contents);
            output.write("\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            }
        }
        return tempfile.getPath();
    }

    /**
     * 指定したURL(ファイルパス)のデータを読み込む
     * @param textUrl
     */
    private void readFile(String path) {
        try {
            File file = new File(path);
            readText = file.getName() + "\n";
            BufferedReader bufferReader = new BufferedReader(new FileReader(
                    file));
            String StringBuffer;
            String stringText = "";
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
            readText += stringText;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            readText += e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            readText += e.toString();
        }
        textView.setText(readText);
    }
}
