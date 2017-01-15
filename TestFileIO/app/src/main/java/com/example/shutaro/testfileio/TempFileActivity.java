package com.example.shutaro.testfileio;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import android.view.View.OnClickListener;

/**
 * キャッシュファイルの読み書きテスト
 */
public class TempFileActivity extends AppCompatActivity implements OnClickListener {

    private TextView mTextView;
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_file);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        mTextView = (TextView)findViewById(R.id.textView);

        printTempfile();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    /**
     * キャッシュファイル書き込みテスト
     */
    private void test1() {
        File internalCachedir = getCacheDir();
        File file = new File(internalCachedir.getPath(), "text1.txt");

        String ret = writeContents(file, "hoge");
        mTextView.setText(ret);
    }

    /**
     * キャッシュファイル読み込みテスト
     */
    private void test2() {
        File internalCachedir = getCacheDir();
        File file = new File(internalCachedir.getPath(), "text1.txt");

        String ret = readFile(file);
        mTextView.setText(ret);
    }

    private void test3() {
        File externalCachedir = getExternalCacheDir();
        File file = new File(externalCachedir.getPath(), "text1.txt");

        String ret = writeContents(file, "hoge");
        mTextView.setText(ret);
    }
    private void test4() {
        File externalCachedir = getExternalCacheDir();
        File file = new File(externalCachedir.getPath(), "text1.txt");

        String ret = readFile(file);
        mTextView.setText(ret);
    }
    private void test5() {
        File externalCachedir = getExternalCacheDir();
        File file = new File(externalCachedir.getPath(), "text1.dat");

        writeBinary(file.getPath());
    }
    private void test6() {
        File externalCachedir = getExternalCacheDir();
        File file = new File(externalCachedir.getPath(), "text1.dat");

        byte[] data = readBinary(file.getPath());

        // 文字列に変換して出力する
        int cnt = 0;
        StringBuffer buf = new StringBuffer();
        for (byte b : data) {
            buf.append(String.format("%02x ", b));
            cnt++;
            if (cnt % 8 == 0) {
                buf.append("\n");
            }
        }
        mTextView.setText(buf.toString());
    }

    private void printTempfile() {
        File internalCachedir = getCacheDir();
        File externalCachedir = getExternalCacheDir();

        StringBuilder buf = new StringBuilder();
        buf.append("internal Cache Dire:\n").append(internalCachedir.getPath())
                .append("\n\n");
        buf.append("external Cache Dire:\n").append(externalCachedir.getPath())
                .append("\n");

        mTextView.setText(buf.toString());
    }

    /**
     * ファイルに保存する
     * @param contents
     * @return
     */
    private String writeContents(File tempfile, String contents) {
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
    private String readFile(File tempfile) {
        StringBuffer strBuf = new StringBuffer();
        try {
            strBuf.append(tempfile.getName() + "\n");
            BufferedReader bufferReader = new BufferedReader(new FileReader(
                    tempfile));
            String StringBuffer;
            String stringText = "";
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
            strBuf.append(stringText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            strBuf.append(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            strBuf.append(e.toString());
        }
        return strBuf.toString();
    }

    // バイナリファイルを読み込み
    // RandomAccessFile
    public byte[] readBinary(String filePath) {
        try {
            // 入力ストリームの生成
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");

            // 読み込み位置を指定する
            raf.seek(0);

            // データを読み込む
            // (読み込んだサイズ分読み込み位置がすすむ)
            byte[] readData = new byte[(int)raf.length()];
            int len = raf.read(readData);

            // 後始末
            raf.close();

            return readData;
        } catch(FileNotFoundException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }

    // ファイルにバイナリを書き込む
    public void writeBinary(String fileName) {
        try {
            System.out.println("writeBinary2");
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");

            // 書き込み
            for (int i=0; i<16; i++) {
                raf.writeInt(i);
            }

            raf.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
