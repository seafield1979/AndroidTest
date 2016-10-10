package com.example.shutaro.testservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService{
    final static String TAG = "MyService";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * サービスが立ち上がると自動で実行される処理
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            // 時間のかかる処理を行うと呼び出し元のスレッドが固まる
            for (int i=0; i<3; i++) {
                Thread.sleep(1000);
                Log.d(TAG, "sleep " + i);
            }
        } catch (InterruptedException e) {
        }
    }
}
