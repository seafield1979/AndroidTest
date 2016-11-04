package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by shutaro on 2016/10/22.
 */

public class IconRect extends IconBase {

    public IconRect(int x, int y, int width, int height) {
        super(IconShape.RECT, x,y,width,height);

        color = Color.rgb(0,255,255);
    }

    public boolean draw(Canvas canvas,Paint paint)
    {
        return draw(canvas, paint, null, null);
    }

    public boolean draw(Canvas canvas,Paint paint, PointF toScreen, RectF clipRect) {
        if (toScreen == null) {
            toScreen = new PointF(0, 0);
        }
        float drawX = pos.x + toScreen.x;
        float drawY = pos.y + toScreen.y;
        RectF rect = new RectF(drawX, drawY, drawX + size.width, drawY + size.height);

        if (clipRect != null) {
            if (isClip(rect, clipRect)) {
                return false;
            }
        }

        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(color);

        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom,  paint);

        drawId(canvas, paint);
        return true;
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
