package com.example.shutaro.testedittext;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private int[] buttonIds = {
            R.id.button1,
            R.id.button2,
            R.id.button3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする

            // Set event listener
            for (int id : buttonIds) {
                findViewById(id).setOnClickListener(this);
            }

            showFragment(new Fragment1(), "fragment1");
        }
    }

    private void showFragment(Fragment fragment, String name) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // コンテナにMainFragmentを格納
        transaction.replace(R.id.fragment_container, fragment, name);
        // 画面に表示
        transaction.commit();

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button1:
                showFragment(new Fragment1(), "fragment1");
                break;
            case R.id.button2:
                showFragment(new Fragment2(), "fragment2");
                break;
            case R.id.button3:
                showFragment(new Fragment3(), "fragment3");
                break;

        }
    }
}
