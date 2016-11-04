package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

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

    public void draw(Canvas canvas,Paint paint) {
        draw(canvas, paint, null);
    }

    public void draw(Canvas canvas,Paint paint, PointF toScreen) {
        if (toScreen == null) {
            toScreen = new PointF(0, 0);
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
    }
}
