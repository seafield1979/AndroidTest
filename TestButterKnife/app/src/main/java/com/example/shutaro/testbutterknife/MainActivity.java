package com.example.shutaro.testbutterknife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myLog";

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                Fragment newFragment = new MyFragment();
                showFragment(newFragment);
            }
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.button5:
                break;
            case R.id.button6:
                break;
        }
    }


    // 表示するフラグメントを切り替える
    private void showFragment(Fragment fragment) {
        // FragmentManagerからFragmentTransactionを作成
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Fragmentを組み込む
        transaction.replace(R.id.fragment_container, fragment);

        // backstackに追加
        // これで戻るボタンで以前のフラグメントが表示されるようになる
        transaction.addToBackStack(null);

        // 上記の変更を反映する
        transaction.commit();
    }
}
