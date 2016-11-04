package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

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
    public static final String TAG = "ScrollBar";

    private ScrollBarType type;

    private float x, y;
    private int contentLen;       // コンテンツ領域のサイズ
    private int viewLen;          // 表示画面のサイズ
    private float topPos;         // スクロールの現在の位置
    private boolean isDraging;

    private int bgLength, bgWidth;

    private float barPos;       // バーの座標
    private int barLength;       // バーの長さ(縦バーなら高さ、横バーなら幅)

    private int bgColor, barColor;

    // 縦のスクロールバーか？
    private boolean isVertical() {
        return (type == ScrollBarType.Left || type == ScrollBarType.Right);
    }
    // 横のスクロールバーか？
    private boolean isHorizontal() {
        return (type == ScrollBarType.Top || type == ScrollBarType.Bottom);
    }

    // Get/Set

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public float getTopPos() {
        return topPos;
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
        topPos = 0;
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
        updateSize(viewWidth, viewHeight);
    }

    /**
     * スクロールバーを表示する先のViewのサイズが変更された時の処理
     * @param viewWidth
     * @param viewHeight
     */
    public void updateSize(int viewWidth, int viewHeight) {
        switch (type) {
            case Top:
                x = 0;
                bgLength = viewWidth;
                break;
            case Bottom:
                x = 0;
                bgLength = viewWidth;
                y = viewHeight - bgWidth - 200;
                break;
            case Left:
                y = 0;
                bgLength = viewHeight;
                break;
            case Right:
                x = viewWidth - bgWidth;
                y = 0;
                bgLength = viewHeight;
                break;
        }
        barLength = (int)(bgLength * ((float)viewLen / (float)contentLen));
        barPos = 0;
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
     * ※外部のスクロールを反映させる
     * @param topPos
     */
    public void updateScroll(PointF topPos) {
        float _pos = isVertical() ? topPos.y : topPos.x;
        barPos = (_pos / (float)contentLen) * bgLength;
        this.topPos = _pos;
    }

    public void updateScroll(float topPos) {
        barPos = (topPos / (float)contentLen) * bgLength;
        this.topPos = topPos;
    }

    /**
     * バーの座標からスクロール量を求める
     * updateScrollの逆バージョン
     */
    public void updateScrollByBarPos() {
        topPos = (barPos / viewLen) * contentLen;
    }

    /**
     * コンテンツやViewのサイズが変更された時の処理
     */
    public void updateContent(Size contentSize) {
        if (isVertical()) {
            this.contentLen = contentSize.height;
        } else {
            this.contentLen = contentSize.width;
        }

        barLength = (int)(this.bgLength * ((float)viewLen / (float)contentLen));
    }

    public void draw(Canvas canvas, Paint paint, PointF winPos) {
        paint.setStyle(Paint.Style.FILL);

        RectF bgRect = new RectF();
        RectF barRect = new RectF();

        float baseX = x + winPos.x;
        float baseY = y + winPos.y;

        if (isHorizontal()) {
            bgRect.left = baseX;
            bgRect.right = baseX + bgLength;
            bgRect.top = baseY;
            bgRect.bottom = baseY + bgWidth;
            barRect.left = baseX + barPos;
            barRect.top = baseY + 10;
            barRect.right = baseX + barPos + barLength;
            barRect.bottom = baseY + bgWidth - 10;
        } else {
            bgRect.left = baseX;
            bgRect.top = baseY;
            bgRect.right = baseX + bgWidth;
            bgRect.bottom = baseY + bgLength;
            barRect.left = baseX + 10;
            barRect.top = baseY + barPos;
            barRect.right = baseX + bgWidth - 10;
            barRect.bottom =baseY + barPos + barLength;
        }

        // 背景
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);
        canvas.drawRect(bgRect.left,
                bgRect.top,
                bgRect.right,
                bgRect.bottom,
                paint);

        // バー
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(barColor);
        canvas.drawRect(barRect.left,
                barRect.top,
                barRect.right,
                barRect.bottom,
                paint);
    }


    /**
     * １画面分上（前）にスクロール
     */
    public void scrollUp() {
        topPos -= viewLen;
        if (topPos < 0) {
            topPos = 0;
        }
        updateScroll(topPos);
    }

    /**
     * １画面分下（先）にスクロール
     */
    public void scrollDown() {
        topPos += viewLen;
        if (topPos + viewLen > contentLen) {
            topPos = contentLen - viewLen;
        }
        updateScroll(topPos);
    }

    /**
     * バーを移動
     * @param move 移動量
     */
    public void barMove(float move) {
        barPos += move;
        if (barPos < 0) {
            barPos = 0;
        }
        else if (barPos + barLength > bgLength) {
            barPos = bgLength - barLength;
        }

        updateScrollByBarPos();
    }

    /**
     * タッチ系の処理
     * @param tv
     * @return
     */
    public boolean touchEvent(ViewTouch tv) {
        MyLog.print(TAG, "vt : " + tv.type);
        switch(tv.type) {
            case Touch:
                if (touchDown(tv)) {
                    return true;
                }
                break;
            case Moving:
                if (touchMove(tv)) {
                    return true;
                }
                break;
            case MoveEnd:
                touchUp();
                break;
        }
        return false;
    }

    /**
     * スクロールバーのタッチ処理
     * @param vt
     * @return true:バーがスクロールした
     */
    private boolean touchDown(ViewTouch vt) {
        // スペース部分をタッチしたら１画面分スクロール
        float ex = vt.touchX();
        float ey = vt.touchY();

        if (isVertical()) {
            if (x <= ex && ex < x + bgWidth &&
                y <= ey && ey < y + bgLength)
            {
                if (ey < barPos) {
                    // 上にスクロール
                    MyLog.print(TAG, "Scroll Up");
                    scrollUp();
                    return true;
                } else if (ey > y + barPos + barLength) {
                    // 下にスクロール
                    MyLog.print(TAG, "Scroll Down");
                    scrollDown();
                    return true;
                } else {
                    // バー
                    MyLog.print(TAG, "Drag Start");
                    isDraging = true;
                    return true;
                }
            }
        } else {
            if (x <= ex && ex < x + bgLength &&
                    y <= ey && ey < y + bgWidth)
            {
                if (ex < barPos) {
                    // 上にスクロール
                    MyLog.print(TAG, "Scroll Up");
                    scrollUp();
                    return true;
                } else if (ex > x + barPos + barLength) {
                    // 下にスクロール
                    MyLog.print(TAG, "Scroll Down");
                    scrollDown();
                    return true;
                } else {
                    // バー
                    MyLog.print(TAG, "Drag Start");
                    isDraging = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean touchUp() {
        MyLog.print(TAG, "touchUp");
        isDraging = false;

        return false;
    }

    private boolean touchMove(ViewTouch vt) {
        if (isDraging) {
            float move = isVertical() ? vt.moveY : vt.moveX;
            barMove(move);
            return true;
        }
        return false;
    }
}
