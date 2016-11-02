package com.sunsunsoft.shutaro.testview;

import android.graphics.PointF;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

enum TouchType {
    None,
    Touch,        // タッチ開始
    Click,        // ただのクリック（タップ)
    LongClick,    // 長クリック
    MoveStart,    // 移動開始
    Moving,       // 移動
    MoveEnd,      // 移動終了
    MoveCancel      // 移動キャンセル
}
/**
 * View上のタッチ処理を判定する
 *
 */
public class ViewTouch {
    private static final boolean _DEBUG = false;

    // クリック判定するためのタッチ座標誤差
    public static final int CLICK_DISTANCE = 30;

    // ロングクリックの時間(ms)
    public static final int LONG_CLICK_TIME = 300;

    // 移動前の待機時間(ms)
    public static final int MOVE_START_TIME = 100;

    // 長押しまでの時間(ms)
    public static final int LONG_TOUCH_TIME = 1000;

    public TouchType type;

    private PointF mContentTop;

    private Timer timer;

    // タッチ中にtrueになる
    private boolean isTouching;
    private boolean isLongTouch;

    // タッチ開始した座標
    private float touchX, touchY;

    protected float x, y;       // スクリーン座標
    float moveX, moveY;

    // タッチ開始した時間
    long touchTime;

    // get/set
    public float getCX() { return x + mContentTop.x; }
    public float getCY() { return y + mContentTop.y; }
    public float touchX() {return this.touchX + mContentTop.x;}
    public float touchY() {return this.touchY + mContentTop.y;}


    public ViewTouch() {
        type = TouchType.None;
    }

    /**
     * ロングタッチがあったかどうかを取得する
     * このメソッドを呼ぶと内部のフラグをクリア
     * @return true:ロングタッチ
     */
    public boolean checkLongTouch() {
        // ロングタッチが検出済みならそれを返す
        if (isLongTouch) {
            Log.d("viewtouch", "Long Touch");
            isLongTouch = false;
            return true;
        }
        return false;
    }

    public TouchType checkTouchType(MotionEvent e) {
        return checkTouchType(e, null);
    }
    public TouchType checkTouchType(MotionEvent e, PointF top) {
        if (top == null) {
            top = new PointF(0,0);
        }
        mContentTop = top;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                Log.d("viewtouch", "Touch Down");

                isTouching = true;
                touchX = e.getX();
                touchY = e.getY();
                type = TouchType.Touch;
                touchTime = System.currentTimeMillis();
                startLongTouchTimer();
            }
                break;
            case MotionEvent.ACTION_UP:
            {
                Log.d("viewtouch", "Up");
                isTouching = false;

                timer.cancel();

                if (type == TouchType.Moving) {
                    Log.d("viewtouch", "MoveEnd");
                    type = TouchType.None;
                    return TouchType.MoveEnd;
                } else {
                    float x = (e.getX() - touchX);
                    float y = (e.getY() - touchY);
                    float dist = (float) Math.sqrt(x * x + y * y);

                    if (dist <= CLICK_DISTANCE) {
                        long time = System.currentTimeMillis() - touchTime;

                        if (time <= LONG_CLICK_TIME) {
                            type = TouchType.Click;
                            Log.d("viewtouch", "SingleClick");
                        } else {
                            type = TouchType.LongClick;
                            Log.d("viewtouch", "LongClick");
                        }
                    } else {
                        type = TouchType.None;
                    }
                }
            }
                break;
            case MotionEvent.ACTION_MOVE:
                // 少し同じ位置をタッチ時続けないと移動状態にならない
                boolean moveStart = false;

                // クリックが判定できるようにタッチ時間が一定時間以上、かつ移動距離が一定時間以上で移動判定される
                if ( type != TouchType.Moving) {
                    float dx = (e.getX() - touchX);
                    float dy = (e.getY() - touchY);
                    float dist = (float) Math.sqrt(dx * dx + dy * dy);

                    if (dist >= CLICK_DISTANCE) {
                        long time = System.currentTimeMillis() - touchTime;
                        if (time >= MOVE_START_TIME) {
                            type = TouchType.Moving;
                            moveStart = true;
                            x = touchX;
                            y = touchY;
                        }
                    }
                }
                if ( type == TouchType.Moving) {
                    moveX = e.getX() - x;
                    moveY = e.getY() - y;
                }
                x = e.getX();
                y = e.getY();

                if (moveStart) {
                    Log.d("viewtouch", "MoveStart");
                    return TouchType.MoveStart;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("viewtouch", "Cancel");
                if (type == TouchType.Moving) {
                    type = TouchType.None;
                    return TouchType.MoveCancel;
                }
                break;
        }

        return type;
    }

    /**
     * ロングタッチ検出用のタイマーを開始
     */
    private void startLongTouchTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                timer.cancel();
                if (isTouching) {
                    // ロングタッチを検出する
                    isLongTouch = true;
                    Log.d("viewtouch", "timer Long Touch");
                }
            }
        }, LONG_TOUCH_TIME, 1000);
    }
}
