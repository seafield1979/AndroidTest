package com.example.shutaro.testgesture;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        private Bitmap mBitmap;
        private Region mRegion = new Region();
        private Path mPath = new Path();
        private Paint mPaint = new Paint();

        public MyView(Context context) {
            super(context);

            try {
                mBitmap = BitmapFactory.decodeResource(getResources(), (int)R.drawable.sirokuma);
            } catch (Exception e) {
            }

            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(3);
            mPaint.setStyle(Style.STROKE);

            Rect rect = new Rect(100, 100, 300, 300);
            mPath.addRect(new RectF(rect), Path.Direction.CW);
            mRegion.set(rect);
        }

        protected void onDraw(Canvas canvas) {
            canvas.save();

            canvas.drawBitmap(mBitmap, 0, 0, null);
            canvas.drawPath(mPath, mPaint);

            canvas.restore();
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mRegion.contains((int) ev.getX(), (int) ev.getY())) {
                    Toast.makeText(getContext(), "タッチしました", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            return true;
        }
    }
}
