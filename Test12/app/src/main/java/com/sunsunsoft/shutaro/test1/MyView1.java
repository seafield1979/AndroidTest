package com.sunsunsoft.shutaro.test1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by shutaro on 2016/10/24.
 */

public class MyView1 extends View {
    private Paint paint = new Paint();

    public MyView1(Context context) {
        super(context);
    }
    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onDraw(Canvas canvas) {
        // 背景塗りつぶし
        canvas.drawColor(Color.argb(0,0,0,0));

        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(Color.argb(128,255,0,0));

        for (int i=0; i<10; i++) {
            canvas.drawCircle(300, i * 200, 100, paint);
        }

    }
}
