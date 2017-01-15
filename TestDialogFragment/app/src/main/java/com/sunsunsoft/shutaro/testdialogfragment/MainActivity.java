package com.sunsunsoft.shutaro.testdialogfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DialogFragmentを表示します
        TestDialogFragment dialogFragment = new TestDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),
                TestDialogFragment.class.getSimpleName());
    }
}
