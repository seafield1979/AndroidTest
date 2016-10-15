package com.sunsunsoft.shutaro.testtimer;

import android.util.Log;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.Timer;

/**
 * Created by shutaro on 2016/10/15.
 タイマーで一定時間ごとに処理を行うクラス
 呼び出すにはActivityのクラスから
    startService(new Intent(getBaseContext(), TimerService.class));
 停止するにはActivityのクラスから
    stopService(new Intent(getBaseContext(), TimerService.class));
 */

public class TimerService extends Service{

    final static String TAG = "MyService";
    final int INTERVAL_PERIOD = 2000;
    Timer timer = new Timer();

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

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                Log.d(TAG, "Hello!");
            }
        }, 0, INTERVAL_PERIOD);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
        Log.d(TAG, "onDestroy");
    }
}
