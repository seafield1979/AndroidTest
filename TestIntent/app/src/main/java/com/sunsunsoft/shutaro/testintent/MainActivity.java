package com.sunsunsoft.shutaro.testintent;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする
            TopFragment fragment = new TopFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // コンテナにMainFragmentを格納
            transaction.add(R.id.fragment_container, fragment, TopFragment.FRAMGMENT_NAME);
            // 画面に表示
            transaction.commit();
        }
    }

}
