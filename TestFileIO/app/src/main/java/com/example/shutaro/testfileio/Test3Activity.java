package com.example.shutaro.testfileio;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.File;
import java.util.LinkedList;

public class Test3Activity extends AppCompatActivity implements OnClickListener{

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);

        findViewById(R.id.button).setOnClickListener(this);

        mTextView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.button:
                showTextFile();
                break;
        }
    }

    /**
     * 指定フォルダにあるtextファイルを表示する
     */
    private void showTextFile() {
        File[] files = UFileUtil.getPath(this, DirType.ExternalDocument).listFiles();

        for(File file : files){
            if(file.isFile() && file.getName().endsWith(".xml")){
                mTextView.append(file.getName() + "\n");
            }
        }
    }
}
