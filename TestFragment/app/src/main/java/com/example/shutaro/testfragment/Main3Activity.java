/*
 FragmentTransaction.show() / .hide() でフラグメントの表示を切り替えるサンプル
 Activityを起動時に 全てのフラグメントを add() しておいて、
 表示を切り替える際に
 表示したいものを FragmentTransaction.show(fragmentオブジェクト)
 表示しないものを FragmentTransaction.hide(fragmentオブジェクト)
 する
*/
package com.example.shutaro.testfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // ボタンを押した時の処理を追加
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // FragmentManagerからFragmentTransactionを作成
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(fragment1);
                if (fragment2.isVisible()) {
                    transaction.hide(fragment2);
                }
                if (fragment3.isVisible()) {
                    transaction.hide(fragment3);
                }
                // 上記の変更を反映する
                transaction.commit();
            }
        });
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // FragmentManagerからFragmentTransactionを作成
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(fragment2);
                if (fragment1.isVisible()) {
                    transaction.hide(fragment1);
                }
                if (fragment3.isVisible()) {
                    transaction.hide(fragment3);
                }
                // 上記の変更を反映する
                transaction.commit();
            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // FragmentManagerからFragmentTransactionを作成
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(fragment3);
                if (fragment1.isVisible()) {
                    transaction.hide(fragment1);
                }
                if (fragment2.isVisible()) {
                    transaction.hide(fragment2);
                }
                // 上記の変更を反映する
                transaction.commit();
            }
        });

        // 最初に表示するFragment
        if (savedInstanceState == null) {
            // 実際に使用するFragmentの作成
            // FragmentManagerからFragmentTransactionを作成
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // コンテナにMainFragmentを格納
            this.fragment1 = new Fragment1();
            transaction.add(R.id.fragment_container, fragment1, Fragment1.FRAMGMENT_NAME);

            this.fragment2 = new Fragment2();
            transaction.add(R.id.fragment_container, fragment2, Fragment2.FRAMGMENT_NAME);

            this.fragment3 = new Fragment3();
            transaction.add(R.id.fragment_container, fragment3, Fragment3.FRAMGMENT_NAME);

            // 上記の変更を反映する
            transaction.commit();

        }
    }
}
