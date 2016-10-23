package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;

/**
 * Created by shutaro on 2016/10/22.
 */

/**
 * アイコンの挿入位置に表示する四角形
 */
public class InsertPoint {
    public boolean isShow;
    protected int x,y;
    protected int width,height;
    protected int color;

    public InsertPoint(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        color = Color.argb(64, 255,128,0);
    }

    // 座標、サイズのGet/Set
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getRight() {
        return x + width;
    }
    public int getBottom() {
        return y + height;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int w) {
        width = w;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int h) {
        height = h;
    }

    public void setRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!isShow) return;

        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(1);
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 色
        paint.setColor(color);

        canvas.drawRect((float)x,
                (float)y,
                (float)(x + width),
                (float)(y + height),
                paint);
    }
}
