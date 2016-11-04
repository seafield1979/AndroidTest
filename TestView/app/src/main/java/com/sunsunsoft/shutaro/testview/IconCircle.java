package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by shutaro on 2016/10/22.
 */

public class IconCircle extends IconBase {
    protected int radius;

    public IconCircle(int x, int y, int width) {
        super(IconShape.CIRCLE, x,y,width,width);

        color = Color.rgb(0,255,255);
        this.radius = width / 2;
    }

    public boolean draw(Canvas canvas,Paint paint) {
        return draw(canvas, paint, null, null);
    }

    public boolean draw(Canvas canvas,Paint paint, PointF toScreen, RectF clipRect) {
        if (toScreen == null) {
            toScreen = new PointF(0, 0);
        }
        // クリッピング
        float x = pos.x + toScreen.x;
        float y = pos.y + toScreen.y;
        RectF rect = new RectF(x, y, x + radius * 2, y + radius * 2);
        if (clipRect != null) {
            if (isClip(rect, clipRect)) {
                return false;
            }
        }

        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(color);

        // 塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        // x,yが円を囲む矩形の左上にくるように座標を調整
        canvas.drawCircle(pos.x+radius + toScreen.x, pos.y+radius + toScreen.y, radius, paint);

        drawId(canvas, paint);
        return true;
    }
}
