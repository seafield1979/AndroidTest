package com.example.shutaro.testappwedget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 自分のWidgetの情報を取得
        ComponentName name = new ComponentName(this, NewAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = appWidgetManager.getAppWidgetIds(name);
        for (int id : ids) {
            AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(id);
            Log.d("myLog", info.loadLabel(this.getPackageManager()));
        }

        // 現在インストールされているWidgetのリストを取得
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        List<AppWidgetProviderInfo> widgetList = manager.getInstalledProviders();

        for(AppWidgetProviderInfo info : widgetList) {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//                Log.d("autoAdvanceViewId", info.autoAdvanceViewId + "");
//
//            if(info.configure != null)
//                Log.d("configure", info.configure.flattenToString());

//            Log.d("icon", info.icon + "");
//            Log.d("initialLayout", info.initialLayout + "");
            Log.d("label", info.loadLabel(this.getPackageManager()));
//            Log.d("minHeight", info.minHeight + "");
//            Log.d("minWidth", info.minWidth + "");
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//                Log.d("previewImage", info.previewImage + "");
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
//                Log.d("resizeMode", info.resizeMode + "");
//
//            Log.d("updatePeriodMilis", info.updatePeriodMillis + "");
//
//            if(info.provider != null)
//                Log.d("provider", info.provider.flattenToString());
        }
    }
}
