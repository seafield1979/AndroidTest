package com.example.shutaro.testnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.view.View.OnClickListener;

public class AlarmActivity extends AppCompatActivity {

    private static final int bid1 = 1;
    private static final int bid2 = 2;

    private Button button1, button2, button3;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // 10秒で繰り返しアラーム
        button1 = (Button)this.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                // 5秒後に設定
                calendar.add(Calendar.SECOND, 5);

                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                intent.putExtra("intentId", 1);
                // PendingIntentが同じ物の場合は上書きされてしまうので requestCode で区別する
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid1, intent, 0);

                // アラームをセットする
                AlarmManager am = (AlarmManager) AlarmActivity.this.getSystemService(ALARM_SERVICE);
                // 約10秒で 繰り返し
                am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pending);

                // トーストで設定されたことをを表示
                Toast.makeText(getApplicationContext(), "ALARM 1", Toast.LENGTH_SHORT).show();

                // 無理やりですが、アプリを一旦終了します。この方法はバックグラウンドに移行させるための方便で推奨ではありません
                close();
            }
        });

        // アラームの取り消し
        button2 = (Button)this.findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid1, indent, 0);

                // アラームを解除する
                AlarmManager am = (AlarmManager)AlarmActivity.this.getSystemService(ALARM_SERVICE);
                am.cancel(pending);
                Toast.makeText(getApplicationContext(), "Cancel ALARM 1", Toast.LENGTH_SHORT).show();

            }
        });

        textView = (TextView)findViewById(R.id.text_view);
        // 協定世界時 (UTC)です適宜設定してください

        // 日時を指定したアラーム
        button3 = (Button)this.findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN);
                calendar.add(Calendar.SECOND, 5);

                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                intent.putExtra("intentId", 2);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);

                // アラームをセットする
                AlarmManager am = (AlarmManager)AlarmActivity.this.getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                Toast.makeText(getApplicationContext(), "ALARM 2", Toast.LENGTH_SHORT).show();

                String setTime = String.format("%04d/%02d/%02d %02d:%02d:%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1,  // 0が1月に相当するので +1する
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
                textView.setText(setTime);
            }
        });
    }

    private void close(){
        finish();
    }
}