package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by shutaro on 2016/10/22.
 */

public class IconBmp extends IconBase {

    private Bitmap bmp;

    public IconBmp(int x, int y, int width, int height, Bitmap bmp) {
        super(IconShape.IMAGE, x, y, width, height);

        this.bmp = bmp;
    }

    public void draw(Canvas canvas,Paint paint) {
        draw(canvas, paint, null);
    }

    public void draw(Canvas canvas,Paint paint, PointF top) {
        if (top == null) {
            top = new PointF(0,0);
        }
        if (bmp != null) {
            // そのままのサイズで描画
            //         canvas.drawBitmap(bmp, x, y, paint);

            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(bmp, new Rect(0,0,width, height),
                    new Rect((int)(x - top.x), (int)(y - top.y), (int)x+width,(int)y+height),
                    paint);
        }
        drawId(canvas, paint);
    }
}
