package com.sunsunsoft.shutaro.testsharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private int[] buttonIds = {
            R.id.button,
            R.id.button2,
            R.id.button3
    };

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private EditText editKey;
    private EditText editValue;
    private TextView textLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int id : buttonIds) {
            (findViewById(id)).setOnClickListener(this);
        }

        mPrefs = getSharedPreferences("DataSave", this.MODE_PRIVATE);
        mEditor = mPrefs.edit();

        editKey = (EditText)findViewById(R.id.editText);
        editValue = (EditText)findViewById(R.id.editText2);
        textLog = (TextView)findViewById(R.id.textLog);

    }


    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:
                writeData();
                break;
            case R.id.button2:
                readData();
                break;
            case R.id.button3:
                showData();
                break;
        }
    }

    /**
     * Shared Preferences に値を書き込む
     */
    private void writeData() {
        String key = editKey.getText().toString();
        String value = editValue.getText().toString();

        if (key == null || value == null) return;

        mEditor.putString(key, value);
        mEditor.apply();
    }

    /**
     * Shared Preferences から値を読み込む
     */
    private void readData() {
        String key = editKey.getText().toString();

        if (key == null) return;

        String value = mPrefs.getString(key, "");
        editValue.setText(value);
    }

    /**
     * Shared Preferences の全てのデータを出力する
     */
    private void showData() {
        Map<String,?> keys = mPrefs.getAll();

        textLog.setText("");

        for(Map.Entry<String,?> entry : keys.entrySet()){
            textLog.append(entry.getKey() + ": " + entry.getValue().toString() + "\n");

        }
    }
}
