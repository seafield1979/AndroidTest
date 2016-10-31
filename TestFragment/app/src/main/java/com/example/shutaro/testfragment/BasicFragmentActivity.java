package com.example.shutaro.testfragment;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BasicFragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_fragment);

        if (savedInstanceState == null) {
            BasicFragment fragment = BasicFragment.createInstance("basic fragment", Color.rgb(128,255,255));

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // コンテナにFragmentを格納
            transaction.add(R.id.fragment_container, fragment, BasicFragment.FRAMGMENT_NAME);
            // 画面に表示
            transaction.commit();
        }
    }
}
