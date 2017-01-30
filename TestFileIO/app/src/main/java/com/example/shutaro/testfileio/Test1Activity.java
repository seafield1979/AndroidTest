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
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class Test1Activity extends AppCompatActivity implements OnClickListener {



    /**
     * Consts
     */
    private static final String tempDirName = "temp";
    private static final String tempFileName = "test1.txt";

    /**
     * Member variables
     */
    private int[] button_ids = new int[]{
            R.id.button_get_external_dirs,
            R.id.button_app,
            R.id.button_app_cache,
            R.id.button_app_ext,
            R.id.button_ext_storage,
            R.id.button_ext_doc,
            R.id.button_ext_download,
            R.id.button_write,
            R.id.button_read,
            R.id.button_delete };

    // 書き込み先、読み込み元のパスを表示するTextView
    private TextView textViewDirPath;

    // ログ、読み込んだテキスト等を表示するTextView
    private TextView textViewLog;

    // ファイルに書き込むテキスト
    private EditText editWrite;

    private String readText;
    private DirType dirType = DirType.AppStorage;
    private static final int CODE_PICKFILE_RESULT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        for (int id : button_ids) {
            findViewById(id).setOnClickListener(this);
        }

        textViewDirPath = (TextView)findViewById(R.id.text_path);
        textViewLog = (TextView)findViewById(R.id.text_log);
        editWrite = (EditText)findViewById(R.id.edit_write);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_get_external_dirs:
            {
                File[] dirs = getExternalFilesDirs(null);
                StringBuffer buf = new StringBuffer();
                if (dirs != null) {
                    for (File dir : dirs) {
                        buf.append(dir.toString() + "\n");
                    }
                }
                textViewLog.setText(buf.toString());
            }
                break;
            case R.id.button_app:
                dirType = DirType.AppStorage;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_app_cache:
                dirType = DirType.AppCache;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_app_ext:
                dirType = DirType.AppExternal;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_ext_storage:
                dirType = DirType.ExternalStorage;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_ext_doc:
                dirType = DirType.ExternalDocument;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_ext_download:
                dirType = DirType.ExternalDownload;
                textViewDirPath.setText(UFileUtil.getPath(this, dirType).toString());
                break;
            case R.id.button_write:
                writeToFile();
                break;
            case R.id.button_read:
                readFile();
                break;
            case R.id.button_delete:
                deleteFile();
                break;
        }
    }

    private void writeToFile() {
        String writeText = editWrite.getText().toString();
        String filePath = writeToFile(writeText);
        textViewLog.setText(filePath + "\n" + writeText);
    }


    private void deleteFile() {

    }



    /**
     * ファイルに保存する
     * 保存先のフォルダパスは getPath()で取得する
     * @param contents
     * @return
     */
    private String writeToFile(String contents) {
        File temppath = new File(UFileUtil.getPath(this, dirType), tempDirName);
        if (temppath.exists() != true) {
            if (false == temppath.mkdirs()) {
                return "failed to create directory.\n" + tempDirName;
            }
        }

        File tempfile = new File(temppath, tempFileName);
        FileWriter output = null;
        try {
            output = new FileWriter(tempfile, false);
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
     */
    private void readFile() {
        String path = UFileUtil.getPath(this, dirType).toString() + "/" + tempDirName + "/" + tempFileName;

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
        textViewLog.setText(readText);
    }
}
