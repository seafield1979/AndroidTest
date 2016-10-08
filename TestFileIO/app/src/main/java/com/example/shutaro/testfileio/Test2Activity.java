package com.example.shutaro.testfileio;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class Test2Activity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test2);
//    }

    private static final String CONFIG_NAME = "appconfig";

    private EditText mEditConfigText;
    private CheckBox mCheckConfigCheck1;
    private CheckBox mCheckConfigCheck2;
    private Spinner mSpinnerConfigSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test2);

        mEditConfigText = (EditText) findViewById(R.id.editConfigText);
        mCheckConfigCheck1 = (CheckBox) findViewById(R.id.checkConfigCheck1);
        mCheckConfigCheck2 = (CheckBox) findViewById(R.id.checkConfigCheck2);
        mSpinnerConfigSelect = (Spinner) findViewById(R.id.spinnerConfigSelect);

        loadConfig();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveConfig();
    }

    /**
     * 設定値を読み込む
     *
     */
    private void loadConfig() {
        SharedPreferences pref = getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);

        mEditConfigText.setText(pref.getString("editConfigText", ""));
        mCheckConfigCheck1.setChecked(pref.getBoolean("checkConfigCheck1",
                false));
        mCheckConfigCheck2.setChecked(pref.getBoolean("checkConfigCheck2",
                false));
        mSpinnerConfigSelect
                .setSelection(pref.getInt("spinnerConfigSelect", 0));
    }

    private void saveConfig() {
        SharedPreferences pref = getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("editConfigText", mEditConfigText.getText().toString());
        editor.putBoolean("checkConfigCheck1", mCheckConfigCheck1.isChecked());
        editor.putBoolean("checkConfigCheck2", mCheckConfigCheck2.isChecked());
        editor.putInt("spinnerConfigSelect",
                mSpinnerConfigSelect.getSelectedItemPosition());

        editor.commit();
    }
}
