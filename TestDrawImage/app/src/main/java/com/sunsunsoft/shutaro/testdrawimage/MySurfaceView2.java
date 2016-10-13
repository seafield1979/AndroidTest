package com.sunsunsoft.shutaro.testdrawimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by shutaro on 2016/10/13.
 */

public class MySurfaceView2  extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    static final long FPS = 30;
    static final long FRAME_TIME = 1000 / FPS;
    static final int RECT_R = 100;
    static final int SPEED = 5;

    SurfaceHolder surfaceHolder;
    Thread thread;
    int cx = RECT_R, cy = RECT_R;
    int speed_x = SPEED, speed_y = SPEED;
    int screen_width, screen_height;


    public MySurfaceView2(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
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
        Canvas canvas = null;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.YELLOW);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);

        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();

        while(thread != null){
            // 移動処理。画面の端で跳ね返る
            cx += speed_x;
            if (cx + RECT_R/2 >= screen_width) {
                cx = screen_width - RECT_R/2;
                speed_x *= -1;
            } else if ( cx - RECT_R/2 < 0 ) {
                cx = RECT_R/2;
                speed_x *= -1;
            }

            cy += speed_y;
            if (cy + RECT_R/2 >= screen_height) {
                cy = screen_height - RECT_R/2;
                speed_y *= -1;
            } else if ( cy - RECT_R/2 < 0 ) {
                cy = RECT_R/2;
                speed_y *= -1;
            }

            try{
                loopCount++;
                canvas = surfaceHolder.lockCanvas();

                canvas.drawRect(
                        0, 0,
                        screen_width, screen_height,
                        bgPaint);

                canvas.drawRect(
                        cx - RECT_R/2, cy - RECT_R/2, cx + RECT_R/2, cy + RECT_R/2,
                        paint);

                surfaceHolder.unlockCanvasAndPost(canvas);

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            }
            catch(Exception e){}
        }
    }
}
