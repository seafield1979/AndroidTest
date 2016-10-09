package com.example.shutaro.testgesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.mylibrary.LogListView;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;
    private LogListView mListView;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GestureDetectorインスタンス作成
        mGestureDetector = new GestureDetector(this, mOnGestureListener);

        mListView = (LogListView)findViewById(R.id.listView2);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);
    }

    /**
     * タッチイベントが発生した時の処理
     * @param ev
     * @return
     */
    public boolean onTouchEvent(MotionEvent ev) {
        // GestureDetectorオブジェクトに丸投げする
        mGestureDetector.onTouchEvent(ev);
        return false;
    }

    /**
     * ジェスチャーのリスナー
     */
    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        /**
         * フリック
         * @param event1
         * @param event2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY)
        {
            try {
                if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "左スワイプ", Toast.LENGTH_SHORT)
                            .show();
                    mListView.addLog("Swipe L");
                } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "右スワイプ", Toast.LENGTH_SHORT)
                            .show();
                    mListView.addLog("Swipe R");
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        /**
         * タップ
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mListView.addLog("Single Tap " + e.getX() + " " + e.getY());
            return false;
        }

        /**
         * ダブルタップ
         * @param e
         * @return
         */
        public boolean onDoubleTap(MotionEvent e) {
            mListView.addLog("Double Tap");
            return false;
        }

        /**
         * タッチダウン
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            mListView.addLog("onDown");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mListView.addLog("onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            String message = "onScroll " + (int)e1.getX() + " " + (int)e1.getY() + " " + (int)e2.getX() + " " + (int)e2.getY();
            mListView.addLog(message);
            return false;
        }

        /**
         * 押下時に呼ばれる(押してすぐに動かすと呼ばれない)
         * @param e
         */
        @Override
        public void onShowPress(MotionEvent e) {
            mListView.addLog("onShowPress");
        }

        /**
         * ダブルタップイベント時に呼ばれる(押す・動かす・離す)
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            mListView.addLog("onDoubleTapEvent");
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mListView.addLog("onSingleTapConfirmed");
            return false;
        }
    };

}
