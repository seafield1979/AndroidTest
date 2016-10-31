package com.example.shutaro.testfragment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


/**
 * 基本的なFragment(MainFragment)を表示するためのActivity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            // MainFragmentを生成、表示
            MainFragment fragment = MainFragment.createInstance("hoge", Color.rgb(255,128,0));

            // コンテナにMainFragmentを格納
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment, MainFragment.FRAMGMENT_NAME);
            // 画面に表示
            transaction.commit();
        }
    }
}
