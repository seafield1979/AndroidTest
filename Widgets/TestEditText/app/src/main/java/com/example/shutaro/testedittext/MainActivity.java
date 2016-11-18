package com.example.shutaro.testedittext;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする
            Fragment1 fragment = new Fragment1();
//            Fragment2 fragment = new Fragment2();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // コンテナにMainFragmentを格納
            transaction.add(R.id.fragment_container, fragment, Fragment2.FRAMGMENT_NAME);
            // 画面に表示
            transaction.commit();
        }
    }
}
