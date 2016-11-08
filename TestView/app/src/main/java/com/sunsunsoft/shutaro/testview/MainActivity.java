package com.sunsunsoft.shutaro.testview;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // データを渡す為のBundleを生成し、渡すデータを内包させる
            Bundle bundle = new Bundle();
            bundle.putString("URL", "http://hogehoge.com");

            // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする
            MyFragment11 fragment = new MyFragment11();
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // コンテナにMainFragmentを格納
            transaction.add(R.id.fragment_container, fragment, MyFragment11.FRAMGMENT_NAME);
            // 画面に表示
            transaction.commit();
        }

        MyLog.init();
    }
}
