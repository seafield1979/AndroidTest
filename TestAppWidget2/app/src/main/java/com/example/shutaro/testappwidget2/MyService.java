package com.example.shutaro.testappwidget2;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.Date;

/**
 * Created by shutaro on 2016/10/06.
 */
public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int startFlags, int startId)  {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.clock_app_widget);
        remoteViews.setTextViewText(R.id.textView, new Date().toString());

        ComponentName thisWidget = new ComponentName(this, ClockAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, remoteViews);

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}