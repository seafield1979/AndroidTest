package com.example.shutaro.testcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by shutaro on 2016/09/30.
 */
public class SampleView extends View{
    private Paint paint = new Paint();
    private Path path = new Path();
    private int drawMode;
    private Bitmap mBmp, mBmpImo;

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBmpImo = BitmapFactory.decodeResource(getResources(), R.drawable.imoni_s);
    }

    public void setDrawMode(int mode){
        this.drawMode = mode;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);
        //paint = new Paint();

        switch(this.drawMode){
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
            case 6:
                testCliping(canvas);
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
        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);
        // 色を設定
        paint.setColor(Color.rgb(255,0,0));

        canvas.drawText("Hello!", 50, 100, paint);

        // 斜体
        paint.setTextSkewX(-0.25f);
        paint.setColor(Color.rgb(0,255,0));
        canvas.drawText("Hello!", 50, 200, paint);

        // 太字
        paint.setTextSkewX(0);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setFakeBoldText(true);

        paint.setColor(Color.rgb(0,0,255));
        canvas.drawText("Hello!", 50, 300, paint);
    }


    public void drawLines(Canvas canvas) {
        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);
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
        paint.setAntiAlias(true);
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
     * 四角形を描画する
     * @param canvas
     */
    private void myDrawRect(Canvas canvas, Paint paint, float x, float y, int width, int height, int color)
    {
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(x, y, x + width, y +height, paint);
    }

    /**
     * 円を描画する
     * @param canvas
     */
    private void drawCircles(Canvas canvas) {
        //Randomクラスのインスタンス化
        Random rnd = new Random();

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);
        // 線の種類
        paint.setStyle(Paint.Style.STROKE);
        // 線の太さ
        paint.setStrokeWidth(10);
        // 色
        paint.setColor(Color.argb(128,255,0,0));

        canvas.drawCircle(300, 300, 100, paint);

        // 塗りつぶし
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i=0; i<30; i++) {
            paint.setColor(Color.argb(128,rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));

            canvas.drawCircle((float)(Math.random()*500.0f+150.f),
                    (float)(Math.random()*500.0f+150.f),
                    (float)(Math.random()*150.0f + 50.0f), paint);
        }
    }

    private static void myDrawCircle(Canvas canvas, Paint paint, float x, float y, float radius, int color) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }

    /**
     * 画像を描画する
     * @param canvas
     */
    private void drawImage(Canvas canvas) {

        canvas.drawBitmap(mBmp, 0, 0, paint);

        canvas.scale(1.5f, 1.5f);
        canvas.drawBitmap(mBmp, 50, 50, paint);

        canvas.scale(1.5f, 1.5f);
        canvas.drawBitmap(mBmp, 100, 100, paint);

        canvas.drawBitmap(mBmpImo, 0, 0, paint);

    }

    /**
     * クリッピングテスト
     */
    private void testCliping(Canvas canvas) {
        int width, height;

        canvas.save();

        // クリッピングを設定
        canvas.clipRect(50, 50, 400, 400);

        myDrawCircle(canvas, paint, 150, 150, 200, Color.rgb(0,250,128));
        myDrawCircle(canvas, paint, 50, 50, 100, Color.rgb(128,0,128));

        canvas.restore();

        // 複数のクリッピイング
        canvas.save();

        Path path = new Path();
        path.addCircle(200+150, 200+50, 100, Path.Direction.CCW);
        path.addCircle(200+50, 200+250, 100, Path.Direction.CCW);
        path.addCircle(200+250, 200+250, 100, Path.Direction.CCW);
        canvas.clipPath(path);

        canvas.drawBitmap(mBmpImo, 0, 0, paint);

        canvas.restore();

        // 複数のクリッピイング2
        canvas.save();

        Path path2 = new Path();
        width = 200;
        height = 200;
        path2.addRect( 100, 500, 100+width, 500+height, Path.Direction.CCW);
        path2.addRect( 200, 600, 200+width, 600+height, Path.Direction.CCW);
        canvas.clipPath(path2);

        myDrawRect(canvas, paint, 0,0, 1000, 1000, Color.rgb(120, 50, 200));
        myDrawCircle(canvas, paint, 250, 650, 100, Color.rgb(250, 250, 100));

        canvas.restore();


    }
}
