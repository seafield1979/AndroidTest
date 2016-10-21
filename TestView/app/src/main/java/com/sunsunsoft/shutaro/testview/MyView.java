package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import static android.content.ContentValues.TAG;

/**
 * Created by shutaro on 2016/10/21.
 */

public class MyView extends View implements OnClickListener, OnTouchListener{
    private Paint paint = new Paint();
    private TouchEventCallbacks _callbacks;

    public void setCallbacks(TouchEventCallbacks callbacks){
        _callbacks = callbacks;
    }

    public MyView(Context context) {
        super(context);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onDraw(Canvas canvas) {
        // 背景塗りつぶし
        canvas.drawColor(Color.argb(0,0,0,0));

        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(Color.argb(128,255,0,0));


        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2 - 5, paint);

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
        _callbacks.touchCallback(e.getAction());
        return false;
    }
}
