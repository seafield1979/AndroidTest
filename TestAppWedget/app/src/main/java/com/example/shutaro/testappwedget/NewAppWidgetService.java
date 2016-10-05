package com.example.shutaro.testappwedget;

import android.app.Service;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class NewAppWidgetService extends Service {
    private final String BUTTON1_CLICK_ACTION = "BUTTON1_CLICK_ACTION";
    private final String BUTTON2_CLICK_ACTION = "BUTTON2_CLICK_ACTION";

    @Override
    public int onStartCommand(Intent intent, int startFlags, int startId) {
        super.onStartCommand(intent, startFlags, startId);

        // button1
        // ボタンが押された時に発行されるインテントを準備する
        Intent button1Intent = new Intent();
        Intent button2Intent = new Intent();

        button1Intent.setAction(BUTTON1_CLICK_ACTION);
        button2Intent.setAction(BUTTON2_CLICK_ACTION);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, button1Intent, 0);
        PendingIntent pendingIntent2 = PendingIntent.getService(this, 0, button2Intent, 0);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);

        remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button2, pendingIntent2);

        // ボタンが押された時に発行されたインテントの場合は文字を変更する
        if (BUTTON1_CLICK_ACTION.equals(intent.getAction())) {
            remoteViews.setTextViewText(R.id.appwidget_text, "Pushed 1");
        }

        if (BUTTON2_CLICK_ACTION.equals(intent.getAction())) {
            remoteViews.setTextViewText(R.id.appwidget_text, "Pushed 2");
        }

        // AppWidgetの画面更新
        ComponentName thisWidget = new ComponentName(this, NewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, remoteViews);

        return START_REDELIVER_INTENT;        // サービスが強制終了した際、サービスを再起動しない
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}