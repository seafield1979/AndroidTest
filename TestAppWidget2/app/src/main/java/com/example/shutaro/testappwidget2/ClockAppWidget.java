package com.example.shutaro.testappwidget2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class ClockAppWidget extends AppWidgetProvider {
    public static int count = 0;
    public static int COUNTER = 0;
    private static final long INTERVAL_COUNTDOWN = 1000;
    private final String COUNTDOWN_OPERATION = "COUNTDOWN_OPERATION";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        COUNTER = 0;

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.clock_app_widget);

        ComponentName thisWidget = new ComponentName(context, ClockAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thisWidget, remoteViews);

        setCountdownAlarm(context);
    }

    /**
     * インテントを受け取った時の処理
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        if(intent.getAction().equals(COUNTDOWN_OPERATION)){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.clock_app_widget);
            ComponentName watchWidget = new ComponentName(context, ClockAppWidget.class);

            // ここにタイマーの実処理
            timerMain(remoteViews);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

            // 次のカウントようにタイマーを起動
            setCountdownAlarm(context);
        }
    }

    // タイマーのメイン処理
    private void timerMain(RemoteViews remoteViews) {
        remoteViews.setTextViewText(R.id.textView, "COUNT: " + COUNTER++);
    }

    /**
     * AlertManagerを使用した PendingIntent(ACTION:COUNTDOWN_OPERATION) の発行
     * これを onReceive で受信する
     * @param context
     */
    private void setCountdownAlarm(Context context) {
        PendingIntent operation = getPendingIntent(context, COUNTDOWN_OPERATION);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        long firstTime = System.currentTimeMillis() + 1000 * 1;
        alarmManager.set(AlarmManager.RTC, firstTime, operation);
    }

    /**
     * PendingIntentを作成する
     * @param context
     * @param action
     * @return
     */
    protected PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);

        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}

