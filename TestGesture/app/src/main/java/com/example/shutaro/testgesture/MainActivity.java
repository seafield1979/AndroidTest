package com.example.shutaro.testgesture;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    GestureDetector mGestureDetector;

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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);

        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("myLog", "Single Tap");
        return false;
    }

    public boolean onDoubleTap(MotionEvent e) {
        Log.d("myLog", "Double Tap");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("myLog", "onDown");
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Log.d("myLog", "onFling");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("myLog", "onLongPress");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d("myLog", "onScroll");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("myLog", "onShowPress");
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("myLog", "onDoubleTapEvent");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("myLog", "onSingleTapConfirmed");
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.shutaro.testgesture/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.shutaro.testgesture/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            try {
                if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "左スワイプ", Toast.LENGTH_SHORT)
                            .show();
                } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "右スワイプ", Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("myLog", "Single Tap");
            return false;
        }

        public boolean onDoubleTap(MotionEvent e) {
            Log.d("myLog", "Double Tap");
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("myLog", "onDown");
            return false;
        }
        
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("myLog", "onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            Log.d("myLog", "onScroll");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("myLog", "onShowPress");
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("myLog", "onDoubleTapEvent");
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("myLog", "onSingleTapConfirmed");
            return false;
        }
    };
}
