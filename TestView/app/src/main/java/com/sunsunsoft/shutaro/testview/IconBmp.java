package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by shutaro on 2016/10/22.
 */

public class IconBmp extends IconBase {

    private Bitmap bmp;

    public IconBmp(int x, int y, int width, int height, Bitmap bmp) {
        super(IconShape.IMAGE, x, y, width, height);

        this.bmp = bmp;
    }

    public boolean draw(Canvas canvas,Paint paint) {
        return draw(canvas, paint, null, null);
    }

    public boolean draw(Canvas canvas,Paint paint, PointF toScreen, RectF clipRect) {
        if (toScreen == null) {
            toScreen = new PointF(0,0);
        }
        if (bmp != null) {

            // そのままのサイズで描画
            //         canvas.drawBitmap(bmp, x, y, paint);

            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(bmp, new Rect(0,0,size.width, size.height),
                    new Rect((int)(pos.x + toScreen.x), (int)(pos.y + toScreen.y), (int)pos.x+size.width,(int)pos.y+size.height),
                    paint);
        }
        drawId(canvas, paint);

        return true;
    }
}
