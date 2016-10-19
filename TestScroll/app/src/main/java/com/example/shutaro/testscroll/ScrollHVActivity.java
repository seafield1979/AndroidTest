package com.example.shutaro.testscroll;
/**
 * 縦横にスクロールする
 */

import android.graphics.Color;
import android.net.Uri;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class ScrollHVActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_hv);

        LinearLayout linear1 = (LinearLayout) findViewById(R.id.scrollLayout1);

        for (int i = 0; i < 100; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("hoge %d", i));
            if (i % 2 == 0) {
                tv.setBackgroundColor(Color.rgb(200, 100, 0));
            }

            tv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);

            linear1.addView(tv);

        }
        LinearLayout linear2 = (LinearLayout) findViewById(R.id.scrollLayout2);

        for (int i = 0; i < 100; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("hoge %d", i));
            if (i % 2 == 0) {
                tv.setBackgroundColor(Color.rgb(200, 100, 0));
            }

            tv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);

            linear2.addView(tv);

        }


        GridView gridView = (GridView) findViewById(R.id.gridView);

        String[] WEEK = new String[]{"月", "火", "水", "木", "金", "土", "日", "月", "火", "水", "木", "金", "土", "日", "月", "火", "水", "木", "金", "土", "日", "月", "火", "水", "木", "金", "土", "日"};

        // Adapter作成
        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, WEEK);
        // Adapter設定
        gridView.setAdapter(adapter);

//        String[] weeks = new String[100];
//        for (int i=0; i<100; i++) {
//            weeks[i] = String.format("hoge %d", i);
//
//            // Adapter作成
//            ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, weeks);
//            // Adapter設定
//            gridView.setAdapter(adapter);
//        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ScrollHV Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
