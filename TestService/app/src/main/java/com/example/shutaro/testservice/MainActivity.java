package com.example.shutaro.testservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[3];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3 };

    TextView mTextView;
    MyBindService mService;
    MyBindService.BindServiceBinder mBinder;
    boolean         mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
            buttons[i].setText("test" + String.valueOf(i+1));
        }

        mTextView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
        }
    }

    // コネクション作成
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // サービス接続時に呼ばれる
            Log.i("ServiceConnection", "onServiceConnected");
            // バインダーを保存
            MyBindService.BindServiceBinder binder = (MyBindService.BindServiceBinder) service;
            mService = binder.getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // サービス切断時に呼ばれる
            Log.i("ServiceConnection", "onServiceDisconnected");
            mService = null;
        }
    };

    private void test1() {
        // バインド開始
        boolean ret = bindService( new Intent( MainActivity.this, MyBindService.class ) ,
                connection,
                Context.BIND_AUTO_CREATE
        );
        if (ret) {
            mBound = true;
            mTextView.append("bindService 成功\n");
        } else {
            mTextView.append("bindService 失敗\n");
        }
    }
    private void test2() {
        if( mBound ) {
            // バインドされている場合、バインドを解除
            unbindService(connection);
            mBound = false;
            mTextView.append("bind解除\n");
        } else {
            mTextView.append("bindされていません\n");
        }
    }
    private void test3() {
        if( mBound ){
            // 適当な関数を呼び出し
            String msg = mService.TestFunction();
            mTextView.append(msg);
        } else {
            mTextView.append("bindされていません\n");
        }
    }
}

