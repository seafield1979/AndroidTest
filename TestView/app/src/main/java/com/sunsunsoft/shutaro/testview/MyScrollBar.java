package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


enum ScrollBarType {
    Horizontal,     // 横
    Vertical        // 縦
}
/**
 * 自前で描画するスクロールバー
 * タッチ操作でスクロールできる
 */
public class MyScrollBar {
    private ScrollBarType type;

    private float x, y;
    private int contentLen;
    private int pageLen;
//    private float .;

    private int bgLength, bgWidth;

    private float barPos;       // バーの座標
    private int barLength;       // バーの長さ(縦バーなら高さ、横バーなら幅)

    private int bgColor, barColor;

    public MyScrollBar(ScrollBarType type, float x, float y, int bgLen, int bgWidth, int contentLen, int pageLen) {
        this.type = type;
        this.x = x;
        this.y = y;
        barPos = 0;
        this.bgLength = bgLen;
        this.bgWidth = bgWidth;
        this.contentLen = contentLen;
        this.pageLen = pageLen;

        barLength = (int)(bgLen * ((float)pageLen / (float)contentLen));
        bgColor = Color.argb(128,0,0,0);
        barColor = Color.argb(128, 255,128,0);
    }

    public void draw(Canvas canvas, Paint paint) {
        // 背景
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(bgColor);
        canvas.drawRect(x,
                y,
                x + bgWidth,
                y + bgLength,
                paint);

        // バー
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(barColor);
        canvas.drawRect(x + 10,
                y + barPos,
                x + bgWidth - 20,
                y + barLength,
                paint);
    }

    private void touchDown(MotionEvent e) {
        // スペース部分をタッチしたら１画面分スクロール
        float ex = e.getX();
        float ey = e.getY();
        if (x <= ex && ex < x + bgWidth &&
                y <= ey && ey < y + barLength)
        {
            if (ey < barPos) {

            } else if (ey > barPos + barLength) {

            } else {

            }
        }
    }

    private void touchUp(MotionEvent e) {

    }

    private void touchMove(MotionEvent e) {

    }

    public void touchScroll(MotionEvent e) {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(e);
                break;
            case MotionEvent.ACTION_UP:
                touchUp(e);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(e);
                break;
        }
    }
}
