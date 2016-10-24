package com.sunsunsoft.shutaro.testview;

import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

enum TouchType {
    None,
    Click,        // ただのクリック（タップ)
    LongClick,    // 長押し
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

    // 移動前の待機時間
    public static final int MOVE_START_TIME = 300;

    public TouchType type;

    // タッチ開始した座標
    float touchX, touchY;

    protected float x, y;
    float moveX, moveY;

    // タッチ開始した時間
    long touchTime;

    // get/set
    public float getMoveX() {
        return moveX;
    }
    public float getMoveY() {
        return moveY;
    }

    public ViewTouch() {
        type = TouchType.None;
    }

    public TouchType checkTouchType(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                touchX = e.getX();
                touchY = e.getY();
                type = TouchType.None;
                touchTime = System.currentTimeMillis();
                Log.d("viewtouch", "Touch Down");
            }
                break;
            case MotionEvent.ACTION_UP:
            {
                Log.d("viewtouch", "Up");
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

                if ( type != TouchType.Moving) {
//                    long time = System.currentTimeMillis() - touchTime;
//                    if (time >= MOVE_START_TIME) {
                        type = TouchType.Moving;
                        moveStart = true;
//                    }
                }
                if ( type == TouchType.Moving) {
                    moveX = e.getX() - x;
                    moveY = e.getY() - y;
                    Log.d("viewtouch", "Move");
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
}
