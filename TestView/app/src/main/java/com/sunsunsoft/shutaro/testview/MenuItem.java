package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * メニューに表示する項目
 * アイコンを表示してタップされたらIDを返すぐらいの機能しか持たない
 */

public class MenuItem {
    public static final int ITEM_W = 80;
    public static final int ITEM_H = 80;

    private float x,y;

    // タップされた時に返すID
    private int id;

    // アイコン用画像
    private Bitmap icon;

    // Get/Set
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public MenuItem(int id, Bitmap icon) {
        this.id = id;
        this.icon = icon;
    }

    public void draw(Canvas canvas, Paint paint) {
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(0xffff0000);

        canvas.drawRect(x,
                y,
                x + ITEM_W,
                y + ITEM_H,
                paint);

        if (icon != null) {
            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(icon, new Rect(0,0,ITEM_W, ITEM_H),
                    new Rect((int)x, (int)y, (int)x + ITEM_W,(int)y + ITEM_H),
                    paint);
        }
    }

    public void checkClick(float clickX, float clickY) {

    }
}
