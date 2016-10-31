package com.example.shutaro.testfragment;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * 表示するFragmentを切り替えられる
 * stackModeをONにすると端末の戻るボタンで以前表示していたFragmentに戻る
 */
public class Main2Activity extends AppCompatActivity {
    // 新たにフラグメントを表示する時にスタックに積むかどうか
    public static final boolean stackMode = true;
    private Fragment topFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // ボタンを押した時の処理を追加
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Fragment1を表示
                Fragment newFragment = new Fragment1();
                topFragment = newFragment;
                showFragment(newFragment, false);
            }
        });
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Fragment2を表示
                Fragment newFragment = new Fragment2();
                topFragment = newFragment;
                showFragment(newFragment, false);
            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Fragment3を表示
                Fragment newFragment = new Fragment3();
                topFragment = newFragment;
                showFragment(newFragment, false);
            }
        });

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                removeFragment();
            }
        });

        // 最初に表示するFragment
        if (savedInstanceState == null) {
            // 実際に使用するFragmentの作成
            Fragment newFragment = new Fragment1();
            showFragment(newFragment, true);
        }
    }


    // 表示するフラグメントを切り替える
    private void showFragment(Fragment fragment, boolean isFirst) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Fragmentを組み込む
        transaction.replace(R.id.fragment_container, fragment);

        // backstackに追加
        // これで戻るボタンで以前のフラグメントが表示されるようになる
        if (!isFirst && Main2Activity.stackMode) {
            transaction.addToBackStack(null);
        }

        // 上記の変更を反映する
        transaction.commit();
    }


    /**
     * Fragmentを削除する
     */
    private void removeFragment() {
//        if (topFragment == null) return;
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.remove(topFragment);
//
//        transaction.commit();
//
//        topFragment = null;

        // 一番最初のFragmentに戻る
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
