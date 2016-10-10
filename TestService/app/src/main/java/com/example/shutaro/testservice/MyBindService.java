package com.example.shutaro.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by shutaro on 2016/10/10.
 */
public class MyBindService extends Service {

    static final String TAG= "BindService";

    public class BindServiceBinder extends Binder{
        // サービスの取得
        MyBindService getService(){
            return MyBindService.this;
        }
    }

    // Binderの作成
    private final IBinder mBinder = new BindServiceBinder();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }
    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind");
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent){
        Log.i(TAG, "onUnbind");
        return true;
    }
    @Override
    public void onDestroy(){
        Log.i(TAG, "onDestroy");
    }

    public String TestFunction(){
        String msg = "適当に作った関数が呼ばれました（＾ω＾）\n";
        Log.i(TAG, msg);
        return msg;
    }
}