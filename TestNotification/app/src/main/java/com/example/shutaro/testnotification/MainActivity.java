package com.example.shutaro.testnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.annotation.TargetApi;

public class MainActivity extends AppCompatActivity {
    /** Notificationのタイプ */
    private enum NotificationType {
        BigText, BigPicture, Inbox, Button
    };
    /** Notificationのクリックインテント */
    private static final String ACTION_CLICK_DIALER = "action.click.dialer";
    private static final String ACTION_CLICK_SMS = "action.click.sms";

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.textView)
    TextView textView;

    private static final int bid1 = 1;
    private static final int bid2 = 2;

    private int year, month, date, hour, minute, second, msecond;

    /** Notificationの識別用ID */
    int ID_NOTIFICATION_SAMPLE_ACTIVITY = 0x00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    /**
     * お知らせを追加
     */
    private void test1() {
        showNotification(true);
    }

    /**
     * お知らせを削除
     */
    private void test2() {
        showNotification(false);
    }
    private void test3() {
        showNotification(true, NotificationType.BigText);
    }
    private void test4() {
        showNotification(true, NotificationType.BigPicture);
    }
    private void test5() {
        showNotification(true, NotificationType.Inbox);
    }
    private void test6() {
        showNotification(true, NotificationType.Button);
    }

    /**
     * ステータスバーを表示.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showNotification(boolean isShow) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (isShow) {
            // ステータスバーを通知
            // ブラウザを起動するPendingIntentを生成
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(getString(R.string.url_shoeisha)));
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    ID_NOTIFICATION_SAMPLE_ACTIVITY, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            // Notificationの設定
            Notification.Builder nb = new Notification.Builder(this);
            // 通知イベントのタイムスタンプ
            nb.setWhen(System.currentTimeMillis());
            // コンテンツをセット
            nb.setContentIntent(contentIntent);
            // アイコンをセット
            nb.setSmallIcon(android.R.drawable.stat_notify_chat);
            // 通知時に表示する文字列
            nb.setTicker(getString(R.string.label_status_ticker));
            // ステータスバーに表示するタイトル
            nb.setContentTitle(getString(R.string.label_launch_browser));
            // 音とバイブとライト
            nb.setDefaults(Notification.DEFAULT_SOUND
                    | Notification.DEFAULT_VIBRATE
                    | Notification.DEFAULT_LIGHTS);
            // タップすると自動で表示を消す
            nb.setAutoCancel(true);
            Notification notification = nb.build();

            // Notificationを通知
            notificationManager.notify(ID_NOTIFICATION_SAMPLE_ACTIVITY,
                    notification);
            textView.append("add notify\n");
        } else {
            // ステータスバーを消去
            // Notificationをキャンセル
            notificationManager.cancel(ID_NOTIFICATION_SAMPLE_ACTIVITY);
            textView.append("remove notify\n");
        }
    }

    /**
     * ステータスバーを表示.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(boolean isShow, NotificationType type) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (isShow && type != null) {
            // ステータスバーを通知
            // ブラウザを起動するPendingIntentを生成
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    ID_NOTIFICATION_SAMPLE_ACTIVITY, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            // Notificationの設定
            Notification.Builder nb = new Notification.Builder(this);
            nb.setWhen(System.currentTimeMillis());
            nb.setContentIntent(contentIntent);
            nb.setSmallIcon(android.R.drawable.stat_notify_chat);
            nb.setTicker(getString(R.string.label_status_ticker));
            nb.setContentTitle(getString(R.string.label_status_style));
            nb.setDefaults(Notification.DEFAULT_SOUND
                    | Notification.DEFAULT_VIBRATE);

            Notification notification = null;
            if (type.equals(NotificationType.BigText)) {
                // ビッグテキストのステータスバーを生成
                Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle(
                        nb);
                bigTextStyle.bigText(getString(R.string.label_status_main));
                bigTextStyle
                        .setSummaryText(getString(R.string.label_status_summary));
                notification = bigTextStyle.build();
            } else if (type.equals(NotificationType.BigPicture)) {
                // ビッグピクチャーのステータスバーを生成
                Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle(
                        nb);
                bigPictureStyle.bigPicture(BitmapFactory.decodeResource(
                        getResources(), R.drawable.ch1302_notification_img));
                notification = bigPictureStyle.build();
            } else if (type.equals(NotificationType.Inbox)) {
                // インボックスのステータスバーを生成
                Notification.InboxStyle inboxStyle = new Notification.InboxStyle(
                        nb);
                inboxStyle.addLine(getString(R.string.label_status_showline1));
                inboxStyle.addLine(getString(R.string.label_status_showline2));
                inboxStyle.addLine(getString(R.string.label_status_showline3));
                inboxStyle.addLine(getString(R.string.label_status_showline4));
                inboxStyle
                        .setSummaryText(getString(R.string.label_status_showlinemore));
                notification = inboxStyle.build();
            } else if (type.equals(NotificationType.Button)) {
                // ボタンのステータスバーを生成
                // 電話をかけるIntent
                Intent dialIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:090-1234-5678"));
                dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent dialPending = PendingIntent.getActivity(this,
                        ID_NOTIFICATION_SAMPLE_ACTIVITY, dialIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // メールを送るIntent
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:090-1234-5678"));
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent smsPending = PendingIntent.getActivity(this,
                        ID_NOTIFICATION_SAMPLE_ACTIVITY, smsIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Action action1 = new Notification.Action.Builder(android.R.drawable.ic_dialog_dialer, getString(R.string.label_status_button_call), dialPending).build();
                Notification.Action action2 = new Notification.Action.Builder(android.R.drawable.ic_dialog_email, getString(R.string.label_status_button_sms), smsPending).build();
                nb.addAction(action1);
                nb.addAction(action2);
                notification = nb.build();
            }

            // Notificationを通知
            if (notification != null) {
                notificationManager.notify(ID_NOTIFICATION_SAMPLE_ACTIVITY,
                        notification);
            }
        } else {
            // ステータスバーを消去
            // Notificationをキャンセル
            notificationManager.cancel(ID_NOTIFICATION_SAMPLE_ACTIVITY);
        }
    }
}
