package com.sunsunsoft.shutaro.testtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Test1Activity extends AppCompatActivity {

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
    @InjectView(R.id.textView)
    public TextView textView;

    Timer mTimer;
    final int INTERVAL_PERIOD = 2000;
    Thread mThread1 = null;
    MyRunnable mMyRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
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

    /**
     * タイマー処理
     * 開始/停止
     */
    private void test1() {
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            textView.append("Timer stoped!\n");
        } else {
            textView.append("Timer started!\n");
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("Hello!\n");
                        }
                    });
                }
            }, 0, INTERVAL_PERIOD);
        }
    }

    /**
     * Serviceを立ち上げて、その中でタイマー処理
     */
    private void test2() {
        textView.append("Start Service\n");
        startService(new Intent(getBaseContext(), TimerService.class));
    }

    /**
     * Serviceを停止
     */
    private void test3() {
        textView.append("Stop Service\n");
        stopService(new Intent(getBaseContext(), TimerService.class));
    }

    /**
     * スレッドを立ち上げてそこでSleep処理
     */
    private void test4() {
        if (mThread1 == null) {
            mMyRunnable = new MyRunnable();
            mThread1 = new Thread(mMyRunnable);
            mThread1.start();
        }
    }

    /**
     * スレッドの停止
     */
    private void test5() {
        if (mThread1 != null) {
            mMyRunnable.stopRequest();
            try {
                mThread1.join();
            } catch (InterruptedException e) {
                Log.e("myLog", e.toString());
            }
            mThread1 = null;
        }
    }
    
    private void test6() {
        textView.setText("");
    }
}