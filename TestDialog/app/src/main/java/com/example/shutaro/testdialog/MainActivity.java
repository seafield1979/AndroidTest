package com.example.shutaro.testdialog;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnClickListener{

    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i+1));
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                showMyDialog(0);
                break;
            case R.id.button2:
                showMyDialog(1);
                break;
            case R.id.button3:
                showMyDialog(2);
                break;
            case R.id.button4:
                showMyDialog(3);
                break;
            case R.id.button5:
                showMyDialog(4);
                break;
            case R.id.button6:
                showMyDialog(5);
                break;
        }
    }

    private void showMyDialog(int mode) {
        if (mode == 0) {
            FragmentManager fm = getSupportFragmentManager();
            // ダイアログ用のフラグメントを生成
            AlertFragment af = new AlertFragment();

            // フラグメントにBundle経由で引数を渡す
            Bundle args = new Bundle();
            args.putString("title", "たいとる");
            args.putString("message", "めっせーじ");
            af.setArguments(args);

            // ダイアログを表示
            af.show(fm, "alert_dialog");
        } else if (mode == 1) {
            // OKボタンありダイアログ
            FragmentManager fm = getSupportFragmentManager();
            // ダイアログ用のフラグメントを生成
            Dialog1Fragment dialog1 = new Dialog1Fragment();

            // フラグメントにBundle経由で引数を渡す
            Bundle args = new Bundle();
            args.putString("title", "たいとる");
            args.putString("message", "めっせーじ");
            dialog1.setArguments(args);

            dialog1.show(fm, "alert_dialog");
        } else if (mode == 2) {
            // OKとCancelボタン
            FragmentManager fm = getSupportFragmentManager();
            // ダイアログ用のフラグメントを生成
            Dialog1Fragment dialog1 = new Dialog1Fragment();

            // フラグメントにBundle経由で引数を渡す
            Bundle args = new Bundle();
            args.putString("title", "たいとる");
            args.putString("message", "めっせーじ");
            args.putBoolean("isCancel", true);
            dialog1.setArguments(args);

            // ダイアログを表示
            dialog1.show(fm, "alert_dialog");
        } else if (mode == 3) {
            // 選択項目が３つあるダイアログ
            FragmentManager fm = getSupportFragmentManager();
            Dialog2Fragment dialog2 = new Dialog2Fragment();

            // フラグメントにBundle経由で引数を渡す
            Bundle args = new Bundle();
            args.putString("title", "たいとる");
            dialog2.setArguments(args);

            // ダイアログを表示
            dialog2.show(fm, "dialog2");
        } else if (mode == 4) {
            // 選択項目が３つあるダイアログ
            FragmentManager fm = getSupportFragmentManager();
            Dialog3Fragment dialog3 = new Dialog3Fragment();

            // フラグメントにBundle経由で引数を渡す
            Bundle args = new Bundle();
            args.putString("title", "たいとる");
            dialog3.setArguments(args);

            // ダイアログを表示
            dialog3.show(fm, "dialog2");
        }
    }

    public void onReturnValue(String value) {
        Log.v("myLog", value);
    }
    public void onReturnValue(ArrayList<Integer> list) {
        for (Integer value : list) {
            Log.v("myLog", "checked " + String.valueOf(value));
        }
    }
}

