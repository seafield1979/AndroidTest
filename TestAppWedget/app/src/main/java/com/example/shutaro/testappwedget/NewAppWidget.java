package com.example.shutaro.testappwedget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /*
    ウィジェットが初めて置かれた時と，サイズが変わったときに呼ばれる。
    ウィジェットのサイズを取得できる。
     */
//    void onAppWidgetOptionsChanged(Context context, AppWidgetManager
//            appWidgetManager, int appWidgetId, Bundle newOptions)
//    {
//        super(context, )
//    }

    /*
    このメソッドは，アップウィジェットを更新するために呼ばれる。
    間隔は，AppWidgetProviderInfoの中のupdatePeriodMillisで指定した間隔で呼ばれる。
    また，アップウィジェットをウィジェットを初期化した時にも呼ばれる。
    そのためもし必要であれば，ビューを扱うイベントハンドラーを定義したりするべき。
    ウィジェットの設定Activityを使う場合は，
    アップウィジェットを初期化した時このメソッドが呼ばれないことに注意しないといけない。
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // サービスの起動
        Intent intent = new Intent(context, NewAppWidgetService.class);
        context.startService(intent);
    }

    /*
    初めてアップウィジェットのインスタンスが生成されたときに呼ばれる。
    二つ同じ種類のアップウィジェットを置いたとしても一回しか呼ばれない。
    データベースを初期化生成したリだとか，
    全てのアップウィジェットインスタンスに対して一回やればいいという処理はここ。
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    /*
    アップウィジェットの最後のインスタンスがアップウィジェットホストから消される時呼ばれる。
    一時的なデータベースの削除など，onEnabledメソッドでやったことに対しての後処理はここでやるべきらしい。
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

