package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


enum ScrollBarType {
    Top,
    Bottom,
    Left,
    Right
}
/**
 * 自前で描画するスクロールバー
 * タッチ操作あり
 */
public class MyScrollBar {
    private ScrollBarType type;

    private float x, y;
    private int contentLen;
    private int viewLen;

    private int bgLength, bgWidth;

    private float barPos;       // バーの座標
    private int barLength;       // バーの長さ(縦バーなら高さ、横バーなら幅)

    private int bgColor, barColor;

    /**
     * コンストラクタ
     * 指定のViewに張り付くタイプのスクロールバーを作成
     * @param type
     * @param viewWidth
     * @param viewHeight
     * @param width
     * @param contentLen
     */
    public MyScrollBar(ScrollBarType type, int viewWidth, int viewHeight, int width, int contentLen ) {
        this(type, 0, 0, 0, width, contentLen, viewHeight);

        switch (type) {
            case Top:
                x = 0;
                bgLength = viewWidth;
                break;
            case Bottom:
                x = 0;
                bgLength = viewWidth;
                y = viewHeight - width - 200;
                break;
            case Left:
                y = 0;
                bgLength = viewHeight;
                break;
            case Right:
                x = viewWidth - width;
                y = 0;
                bgLength = viewHeight;
                break;
        }
        barLength = (int)(bgLength * ((float)viewLen / (float)contentLen));
    }

    /**
     * コンストラクタ
     * @param type
     * @param x
     * @param y
     * @param bgLen
     * @param bgWidth
     * @param contentLen
     * @param viewLen
     */
    public MyScrollBar(ScrollBarType type, float x, float y, int bgLen, int bgWidth, int contentLen, int viewLen) {
        this.type = type;
        this.x = x;
        this.y = y;
        barPos = 0;
        this.bgLength = bgLen;
        this.bgWidth = bgWidth;
        this.contentLen = contentLen;
        this.viewLen = viewLen;

        barLength = (int)(bgLength * ((float)viewLen / (float)contentLen));
        bgColor = Color.argb(128,255,255,255);
        barColor = Color.argb(255, 255,128,0);
    }

    /**
     * 色を設定
     * @param bgColor  背景色
     * @param barColor バーの色
     */
    public void setColor(int bgColor, int barColor) {
        this.bgColor = bgColor;
        this.barColor = barColor;
    }

    /**
     * 領域がスクロールした時の処理
     * @param topPos
     */
    public void updateScroll(float topPos) {
        barPos = (topPos / (float)contentLen) * bgLength;
    }

    /**
     * コンテンツやViewのサイズが変更された時の処理
     */
    public void updateContent(Size contentSize, int viewW, int viewH) {
        if (type == ScrollBarType.Top || type == ScrollBarType.Bottom) {
            this.contentLen = contentSize.width;
            this.viewLen = viewW;
        } else {
            this.contentLen = contentSize.height;
            this.viewLen = viewH;
        }

        barLength = (int)(this.bgLength * ((float)viewLen / (float)contentLen));
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        RectF bgRect = new RectF();
        RectF barRect = new RectF();

        switch (type) {
            case Top:
            case Bottom:
                bgRect.left = x;
                bgRect.right = x + bgLength;
                bgRect.top = y;
                bgRect.bottom = y + bgWidth;
                barRect.left = x + barPos;
                barRect.top = y + 10;
                barRect.right = x + x + barPos + barLength;
                barRect.bottom = y + bgWidth - 10;
                break;
            case Left:
            case Right:
                bgRect.left = x;
                bgRect.top = y;
                bgRect.right = x + bgWidth;
                bgRect.bottom = y + bgLength;
                barRect.left = x + 10;
                barRect.top = y + barPos;
                barRect.right = x + bgWidth - 10;
                barRect.bottom = y + barPos + barLength;
                break;
        }

        // 背景
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(bgColor);
        canvas.drawRect(bgRect.left,
                bgRect.top,
                bgRect.right,
                bgRect.bottom,
                paint);

        // バー
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(barColor);
        canvas.drawRect(barRect.left,
                barRect.top,
                barRect.right,
                barRect.bottom,
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
