package com.example.shutaro.testservice;

/**
 * Created by shutaro on 2016/10/10.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    final static String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        try {
            // 時間のかかる処理を行うと呼び出し元のスレッドが固まる
            for (int i=0; i<3; i++) {
                Thread.sleep(1000);
                Log.d(TAG, "sleep " + i);
            }
        } catch (InterruptedException e) {
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}