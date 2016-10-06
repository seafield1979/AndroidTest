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
    private final String ACTION = "ACTION1";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, MyService.class);
        context.startService(intent);
    }

}

