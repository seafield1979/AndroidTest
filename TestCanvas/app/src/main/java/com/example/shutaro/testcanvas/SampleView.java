package com.example.shutaro.testcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.View;

import java.util.Random;

/**
 * Created by shutaro on 2016/09/30.
 */
public class SampleView extends View{
    private Paint paint = new Paint();
    private Path path = new Path();

    public SampleView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onDraw(Canvas canvas) {
        int mode = 5;
        switch(mode){
            case 1:
                testDrawText(canvas);
                break;
            case 2:
                drawLines(canvas);
                break;
            case 3:
                drawRects(canvas);
                break;
            case 4:
                drawCircles(canvas);
                break;
            case 5:
                drawImage(canvas);
                break;
            default:
        }
    }

    /**
     * テキストの描画
     * @param canvas
     */
    private void testDrawText(Canvas canvas) {
        // テキストのサイズを設定
        paint.setTextSize(80);

        // 色を設定
        paint.setColor(Color.rgb(255,0,0));

        // 太字
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setFakeBoldText(true);

        // 斜体
        paint.setTextSkewX(-0.25f);

        canvas.drawText("Hello!", 50, 100, paint);

        // 色を設定
        paint.setColor(Color.rgb(0,255,0));
        canvas.drawText("Hello!", 50, 200, paint);

        // 色を設定
        paint.setColor(Color.rgb(0,0,255));
        canvas.drawText("Hello!", 50, 300, paint);
    }


    public void drawLines(Canvas canvas) {
        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(false);
        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(4);

        // 線
        canvas.drawLine(10, 20, 100, 40, paint);

        // 連続した線
        path.moveTo(0, 100);
        path.lineTo(100, 100);
        path.lineTo(100, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 300);
        paint.setColor(Color.rgb(0,100,255));
        canvas.drawPath(path, paint);
    }

    /**
     * 四角形を描画する
     * @param canvas
     */
    private void drawRects(Canvas canvas) {
        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(false);
        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(Color.rgb(255,0,0));
        canvas.drawRect(50, 50, 300, 300, paint);

        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.rgb(0,180,0));
        canvas.drawRect(50, 350, 500, 500, paint);
    }

    /**
     * 円を描画する
     * @param canvas
     */
    private void drawCircles(Canvas canvas) {
        //Randomクラスのインスタンス化
        Random rnd = new Random();

        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(Color.rgb(255,0,0));

        canvas.drawCircle(300, 300, 100, paint);

        // 塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i=0; i<30; i++) {
            paint.setColor(Color.rgb(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));

            canvas.drawCircle((float)(Math.random()*500.0f+150.f),
                    (float)(Math.random()*500.0f+150.f),
                    (float)(Math.random()*150.0f + 50.0f), paint);
        }
    }

    /**
     * 画像を描画する
     * @param canvas
     */
    private void drawImage(Canvas canvas) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.imoni_s);

        canvas.drawBitmap(bmp, 0, 0, paint);

        canvas.scale(0.5f, 0.5f);
        canvas.drawBitmap(bmp, 0, 0, paint);

        canvas.scale(0.5f, 0.5f);
        canvas.drawBitmap(bmp, 0, 0, paint);

    }
}
