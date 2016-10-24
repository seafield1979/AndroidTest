package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by shutaro on 2016/10/22.
 */

public class MyIconCircle extends MyIcon {
    protected int radius;

    public MyIconCircle(int x, int y, int width) {
        super(IconShape.CIRCLE, x,y,width,width);

        color = Color.rgb(0,255,255);
        this.radius = width / 2;
    }

    public void draw(Canvas canvas, Paint paint) {
        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(color);

        // 塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        // x,yが円を囲む矩形の左上にくるように座標を調整
        canvas.drawCircle(x+radius, y+radius, radius, paint);

        drawId(canvas, paint);
    }
}
