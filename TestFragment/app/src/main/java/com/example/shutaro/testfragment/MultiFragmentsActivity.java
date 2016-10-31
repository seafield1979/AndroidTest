package com.example.shutaro.testfragment;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * １つの画面に複数のFragmentを表示する
 */
public class MultiFragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_fragments);

        if (savedInstanceState == null) {
            // Fragment1
            MultiFragment1 fragment = MultiFragment1.createInstance("hoge1", Color.rgb(255,128,0));

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container1, fragment, MultiFragment1.FRAMGMENT_NAME);

            // Fragment2
            MultiFragment2 fragment2 = MultiFragment2.createInstance("hoge2", Color.rgb(255,128,255));

            transaction.add(R.id.fragment_container2, fragment2, MultiFragment2.FRAMGMENT_NAME);

            transaction.commit();
        }
    }
}
