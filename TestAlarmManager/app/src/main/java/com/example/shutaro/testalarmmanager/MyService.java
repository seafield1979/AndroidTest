package com.example.shutaro.testalarmmanager;

/**
 * Created by shutaro on 2016/10/12.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyService extends IntentService {

    final static String TAG = "ServiceTest3";

    public MyService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
    }
}