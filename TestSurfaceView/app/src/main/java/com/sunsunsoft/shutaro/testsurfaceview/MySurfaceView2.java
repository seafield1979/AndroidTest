package com.sunsunsoft.shutaro.testsurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SurfaceViewのテスト
 *
 * 毎フレームwait()で一時停止してinvalidate()で再描画
 *
 * 画面をドラッグでinvalidate()する
 */

public class MySurfaceView2  extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    static final int RECT_R = 100;
    static final int IMAGE_W = 100;
    static final int SPEED = 5;

    SurfaceHolder surfaceHolder;
    Thread thread;
    private boolean isInvalidate;

    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.hogeman);
    int cx = RECT_R, cy = RECT_R;
    int speed_x = SPEED, speed_y = SPEED;
    int screen_width, screen_height;


    public MySurfaceView2(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        isInvalidate = true;
    }

    // Surfaceが作られた時のイベント処理
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
        Log.i("myLog", "surfaceCreated");
    }

    // Surfaceが変更された時の処理
    @Override
    public void surfaceChanged(
            SurfaceHolder holder,
            int format,
            int width,
            int height) {
        screen_width = width;
        screen_height = height;
        Log.i("myLog", "surfaceChanged");
    }

    // Surfaceが破棄された時の処理
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
        Log.i("myLog", "surfaceDestroyed");
    }

    @Override
    public void run() {
        Canvas canvas;

        long loopCount = 0;
        while(thread != null){
            try{
                synchronized(this) {
                    loopCount++;
                    canvas = surfaceHolder.lockCanvas();

                    if (isInvalidate) {
                        isInvalidate = false;
                        myDraw(canvas);
                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);

                    if (!isInvalidate) {
                        wait();
                    }
                }
            }
            catch(Exception e){}
        }
    }

    public synchronized void invalidate() {
        isInvalidate = true;
        notify();
    }


    public void myDraw(Canvas canvas) {
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.MAGENTA);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);


        // 移動処理。画面の端で跳ね返る
        cx += speed_x;
        if (cx + IMAGE_W >= screen_width) {
            cx = screen_width - IMAGE_W;
            speed_x *= -1;
        } else if ( cx < 0 ) {
            cx = 0;
            speed_x *= -1;
        }

        cy += speed_y;
        if (cy + IMAGE_W >= screen_height) {
            cy = screen_height - IMAGE_W;
            speed_y *= -1;
        } else if ( cy < 0 ) {
            cy = 0;
            speed_y *= -1;
        }

        // Drawing
        canvas.drawRect(
                0, 0,
                screen_width, screen_height,
                bgPaint);

        canvas.drawBitmap(bmp, cx, cy, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean ret = false;

        Log.d("mylog", "event:" + e.getAction());
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // trueを返す。こうしないと以降のMoveイベントが発生しなくなる。
                ret = true;
                break;
            case MotionEvent.ACTION_UP:
                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                ret = true;
                break;
            default:
        }
        // コールバック
        return ret;
    }
}
