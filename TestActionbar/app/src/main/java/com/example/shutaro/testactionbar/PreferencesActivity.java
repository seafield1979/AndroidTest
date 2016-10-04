package com.example.shutaro.testactionbar;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.WindowManager;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (true) {
            // 逆引きレシピのサンプル
            setContentView(R.layout.activity_preferences);
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new MyPreferencesFragment())
                    .commit();

            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
        } else {
            // http://appdevmem.blogspot.jp/2015/09/android-app-settings.htmlのサンプル
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            FragmentTransaction transaction =
                    getFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content,
                    new MyPreferencesFragment());
            transaction.commit();
        }
    }
}
