package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by shutaro on 2016/10/22.
 */

public class IconRect extends IconBase {

    public IconRect(int x, int y, int width, int height) {
        super(IconShape.RECT, x,y,width,height);

        color = Color.rgb(0,255,255);
    }

    public void draw(Canvas canvas,Paint paint) {
        draw(canvas, paint, null);
    }

    public void draw(Canvas canvas,Paint paint, PointF top) {
        if (top == null) {
            top = new PointF(0, 0);
        }

        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(color);

        float drawX = x - top.x;
        float drawY = y - top.y;

        canvas.drawRect(drawX,
                drawY,
                drawX + width,
                drawY + height,
                paint);

        drawId(canvas, paint);
    }

    @Override
    public void click() {
        super.click();
    }

    @Override
    public void longClick() {
        super.longClick();
    }

    @Override
    public void moving() {
        super.moving();

    }
}
