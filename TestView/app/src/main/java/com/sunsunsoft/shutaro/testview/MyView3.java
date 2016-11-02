package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by shutaro on 2016/10/22.
 */

public class MyView3 extends View implements View.OnClickListener, View.OnTouchListener {
    private static final int ICON_NUM = 15;
    private static final int ICON_W = 200;
    private static final int ICON_H = 150;
    private boolean firstDraw = false;

    private Paint paint = new Paint();
    private TouchEventCallbacks _callbacks;
    private MyIcon[] icons = new MyIcon[ICON_NUM];

    public void setCallbacks(TouchEventCallbacks callbacks){
        _callbacks = callbacks;
    }

    public MyView3(Context context) {
        super(context);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    public MyView3(Context context, AttributeSet attrs) {
        super(context, attrs);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i=0; i<ICON_NUM; i++) {
            switch(i % 3) {
                case 0:
                    icons[i] = new IconRect(0, 0, ICON_W, ICON_H);
                    break;
                case 1:
                    icons[i] = new IconCircle(0, 0, ICON_H/2);
                    break;
                case 2:
                    icons[i] = new IconBmp(0, 0, ICON_W, ICON_H, bmp);
                    break;
            }
        }
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

        for (MyIcon icon : icons) {
            if (icon == null) continue;
            icon.draw(canvas, paint);
        }
    }

    /**
     * アイコンを整列する
     * Viewのサイズが確定した時点で呼び出す
     */
    private void sortRects() {
        int column = this.getWidth() / (ICON_W + 20);
        if (column <= 0) {
            return;
        }

        for (int i=0; i<ICON_NUM; i++) {
            if (icons[i] == null) continue;
            int x = (i%column) * (ICON_W + 20);
            int y = (i/column) * (ICON_H + 20);
            icons[i].setPos(x, y);
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
