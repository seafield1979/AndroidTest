package com.sunsunsoft.shutaro.testsurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ダブルバッファでちらつきを防止する
 */

public class MySurfaceView3  extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    static final long FPS = 60;
    static final int RECT_R = 100;
    static final int IMAGE_W = 100;
    static final int SPEED = 5;

    SurfaceHolder surfaceHolder;
    Thread thread;

    Bitmap mOffScreen;
    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.hogeman);
    int cx = RECT_R, cy = RECT_R;
    int speed_x = SPEED, speed_y = SPEED;
    int screen_width, screen_height;

    public MySurfaceView3(Context context) {
        this(context, null);
    }
    public MySurfaceView3(Context context, AttributeSet attr) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    // Surfaceが作られた時のイベント処理
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();

        // オフスクリーン用のBitmapを生成する
        mOffScreen = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);

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

        // Bitmapを解放する
        if (mOffScreen != null)
            mOffScreen.recycle();
    }

    @Override
    public void run() {
        Canvas canvas = null;

        long loopCount = 0;
        long waitTime = 0;
        long preTime;

        while(thread != null){
            preTime = System.currentTimeMillis();
            try{
                synchronized(this) {
                    loopCount++;
                    canvas = surfaceHolder.lockCanvas();

                    // オフスクリーンバッファに描画する
                    if (mOffScreen != null) {
                        // オフスクリーンバッファを生成する
                        Canvas offScreen = new Canvas(mOffScreen);

                        myDraw(offScreen);
                    }

                    // オフスクリーンバッファを描画する
                    canvas.drawBitmap(mOffScreen, 0, 0, null);

                    surfaceHolder.unlockCanvasAndPost(canvas);

                    waitTime = (int)(1000.0f / (float)FPS)
                            - (System.currentTimeMillis() - preTime);

                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                        Log.d("mylog", "waitTime:" + waitTime);
                    }
                }
            }
            catch(Exception e){}
        }
    }

    public synchronized void resume() {
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
                resume();
                ret = true;
                break;
            default:
        }

        // コールバック
        return ret;
    }
}
