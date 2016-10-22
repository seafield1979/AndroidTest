package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * 自前でオブジェクトを描画するView
 */
public class MyView2 extends View implements View.OnClickListener, View.OnTouchListener {

    private static final int RECT_NUM = 10;
    private static final int RECT_W = 200;
    private static final int RECT_H = 150;
    private boolean firstDraw = false;

    private Paint paint = new Paint();
    private TouchEventCallbacks _callbacks;

    private Rect[] rects = new Rect[RECT_NUM];

    public void setCallbacks(TouchEventCallbacks callbacks){
        _callbacks = callbacks;
    }

    public MyView2(Context context) {
        super(context);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }
    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (firstDraw == false) {
            firstDraw = true;
            sortRects();
        }

        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);
        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 色
        paint.setColor(Color.rgb(0,180,0));

        for (int i=0; i<RECT_NUM; i++) {
            canvas.drawRect((float)rects[i].left,(float)rects[i].top,(float)rects[i].right,(float)rects[i].bottom, paint);
        }
    }

    private void sortRects() {
        int column = this.getWidth() / RECT_W;
        if (column <= 0) {
            return;
        }

        for (int i=0; i<RECT_NUM; i++) {
            int x = (i%column) * RECT_W;
            int y = (i/column) * RECT_H;
            rects[i] = new Rect(x, y, x + RECT_W - 20, y + RECT_H - 20);
        }
    }

    public void onClick(View v) {
        Log.v(TAG,"onClick");
    }


    public boolean onTouch(View v, MotionEvent e) {
        Log.v("mylog", "MyView onTouch");
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
        // コールバック
        if (_callbacks != null) {
            _callbacks.touchCallback(e.getAction());
        }
        return false;
    }
}
