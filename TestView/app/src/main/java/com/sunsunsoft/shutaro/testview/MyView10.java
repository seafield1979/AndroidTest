package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.LinkedList;

/**
 * メニューバー、サブViewのサンプル


  */
public class MyView10 extends View implements OnTouchListener, MenuItemCallbacks{
    public static final String TAG = "MyView10";

    // IconWindow
    private IconWindow mIconWin;

    // メニューバー
    private MenuBar mMenuBar;

    // サイズ更新用
    private boolean resetSize;
    private int newWidth, newHeight;

    // クリック判定の仕組み
    private ViewTouch viewTouch = new ViewTouch();

    private Paint paint = new Paint();

    // get/set

    /**
     * Viewのサイズを更新する
     * @param width
     * @param height
     */
    public void updateViewSize(int width, int height) {
        resetSize = true;
        newWidth = width;
        newHeight = height;

        mIconWin.setSize(width, height);
        setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    public MyView10(Context context) {
        this(context, null);
    }

    public MyView10(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);

        if (mIconWin.draw(canvas, paint)) {
            invalidate();
        }

        // メニューバー
        if (mMenuBar.doAction()) {
            invalidate();
        }

        mMenuBar.draw(canvas, paint);
    }

    /**
     * Viewのサイズを指定する
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewW = MeasureSpec.getSize(widthMeasureSpec);
        int viewH = MeasureSpec.getSize(heightMeasureSpec);

        // メニューバー
        if (mMenuBar == null) {
            mMenuBar = new MenuBar(this, this, viewW, viewH);
            mMenuBar.initMenuBar();
        }

        if (mIconWin == null) {
            mIconWin = new IconWindow();
            mIconWin.createWindow(viewW, viewH - 500);
        } else {
            mIconWin.setSize(viewW, viewH);
        }

        if (resetSize) {
            int width = MeasureSpec.EXACTLY | newWidth;
            int height = MeasureSpec.EXACTLY | newHeight;
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    /**
     * タッチイベント処理
     * @param v
     * @param e
     * @return
     */
    public boolean onTouch(View v, MotionEvent e) {
        boolean ret = true;

        viewTouch.checkTouchType(e);

        if (mMenuBar.touchEvent(viewTouch)) {
            invalidate();
        }
        else if (mIconWin.touchEvent(viewTouch)) {
            invalidate();
        }

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // trueを返す。こうしないと以降のMoveイベントが発生しなくなる。
                ret = true;
                break;
            case MotionEvent.ACTION_UP:
                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:
                ret = true;
                break;
            default:
        }

        // コールバック
        return ret;
    }

    /**
     * メニューアイテムをタップした時のコールバック
     */
    public void callback1(MenuItemId id) {
        MyLog.print(TAG, "menu item clicked " + id);
    }

    public void callback2() {
        MyLog.print(TAG, "menu item moved");
    }
}
