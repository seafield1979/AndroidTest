package com.sunsunsoft.shutaro.testtimer;

import android.util.Log;

/**
 * Runnableインターフェースをimplementsしたクラスでrunメソッドに処理を記述する
 */
class MyRunnable implements Runnable {
    final int INTERVAL_PERIOD = 2000;
    private int mCount = 0;
    private boolean running = true;


    public void run() {
        Log.v("myLog", "thread1 start");
        while(running) {
            Log.v("myLog", "count " + mCount);
            mCount++;
            try {
                Thread.sleep(INTERVAL_PERIOD);
            } catch (InterruptedException e) {
            }
        }
        Log.v("myLog", "thread1 stop");
    }

    public void stopRequest() {
        running = false;
    }
}

