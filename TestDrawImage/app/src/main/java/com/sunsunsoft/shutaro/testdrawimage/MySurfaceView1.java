package com.sunsunsoft.shutaro.testdrawimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

import static android.content.ContentValues.TAG;

/**
 * Created by shutaro on 2016/10/13.
 */
public class MySurfaceView1 extends SurfaceView implements Runnable,OnTouchListener,SurfaceHolder.Callback {
    static final long FPS = 30;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = 30;

    SurfaceHolder surfaceHolder;
    Thread thread;
    Context mContext;
    int cx = 100, cy = 100;
    int screen_width, screen_height;

    public MySurfaceView1(Context context) {
        super(context);

        mContext = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    // Surfaceが作られた時のイベント処理
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.setOnTouchListener(this);
        thread = new Thread(this);
        thread.start();
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
    }

    // Surfaceが破棄された時の処理
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    // スレッド処理
    @Override
    public void run() {
        Canvas canvas = null;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();

        // 描画ループ
        while(thread != null){
            try{
                loopCount++;
                // 描画処理開始

                canvas = surfaceHolder.lockCanvas();
                // 背景
                canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);
                // オブジェクト
                canvas.drawCircle(cx, cy, BALL_R, paint);

                // 描画処理終了
                surfaceHolder.unlockCanvasAndPost(canvas);

                // 次のフレーム開始まで
                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            }
            catch(Exception e){}
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e){
        String action = "";

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                break;
            default:
                action = "" + e.getAction();
        }

        Log.v(TAG, "action:" + action + " x:" + e.getX() + " y:" + e.getY());
        return true;
    }
}
