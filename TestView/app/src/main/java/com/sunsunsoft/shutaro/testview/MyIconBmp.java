package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by shutaro on 2016/10/22.
 */

public class MyIconBmp extends MyIcon {

    private Bitmap bmp;

    public MyIconBmp(int x, int y, int width, int height, Bitmap bmp) {
        super(IconShape.IMAGE, x, y, width, height);

        this.bmp = bmp;
    }

    public void draw(Canvas canvas,Paint paint) {
        if (bmp != null) {
            // そのままのサイズで描画
   //         canvas.drawBitmap(bmp, x, y, paint);

            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(bmp, new Rect(0,0,width, height),new Rect((int)x,(int)y,(int)x+width,(int)y+height), paint);
        }
        drawId(canvas, paint);
    }
}
