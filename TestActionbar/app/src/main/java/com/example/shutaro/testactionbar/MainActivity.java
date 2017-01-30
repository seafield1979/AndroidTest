package com.example.shutaro.testactionbar;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6};

    private boolean mShowMenu = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i + 1));
        }
    }

    public void onClick(View v) {
        Log.v("myLog", String.valueOf(v));
        switch (v.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    private void test1() {
        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setTitle("たいとるだ");
            ab.setSubtitle("さぶたいとるだ");
        }
    }

    /**
     * アイコンを表示する
     */
    private void test2() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    /**
     * アクションバーの左に戻るボタンを追加
     */
    private void test3() {
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * メニューの表示切り替え
     */
    private void test4() {
        mShowMenu = !mShowMenu;
        // オプションメニューを書き換える
        invalidateOptionsMenu();
    }

    private void test5() {
    }

    private void test6() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // メニューボタンを生成する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // main.xml から生成
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * メニューの表示切り替え
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return mShowMenu;
    }

    // メニューの項目が選択されると呼ばれる
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        String name = (String)item.getTitle();

        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_hp:
            case R.id.action_s3:
                Toast toast = Toast.makeText(this, "pushed " + name, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
