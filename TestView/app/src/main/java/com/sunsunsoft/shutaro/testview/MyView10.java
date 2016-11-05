package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

/**
 * メニューバー、サブViewのサンプル

  */
public class MyView10 extends View implements OnTouchListener, MenuItemCallbacks{
    public static final String TAG = "MyView10";

    // IconWindow
    private IconWindow[] mWindows = new IconWindow[2];

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

        setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    public void updateWindowSize(int width, int height) {
        for (IconWindow win : mWindows) {
            win.updateSize(width, height);
        }
        invalidate();
    }

    public void updateWindowPos(float x, float y){
        for (IconWindow win : mWindows) {
            win.setPos(x, y, true);
        }
        invalidate();
    }

    /**
     * IconWindowを１つだけ表示
     * @param width
     * @param height
     */
    public void updateShow(int width, int height) {
        mWindows[0].updateSize(width, height);
        mWindows[1].setShow(false);
        invalidate();
    }

    /**
     * IconWindowを２つ表示
     * @param width
     * @param height
     */
    public void updateShow2(int width, int height) {
        mWindows[0].setShow(true);
        mWindows[0].updateSize(width, (height - 100)/2);
        mWindows[1].setShow(true);
        mWindows[1].updateSize(width, (height - 100)/2);
        invalidate();
    }

    public void moveTest1(){
        mWindows[1].startMove(0, getHeight(), 15);
        invalidate();
    }

    public void moveTest2(){
        mWindows[1].startMove(0, (getHeight() - 100) / 2, 15);
        invalidate();
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

        // アイコンWindow
        // アクション
        for (Window win : mWindows) {
            if (win.doAction()) {
                invalidate();
            }
        }

        // 描画処理
        for (Window win : mWindows) {
            if (!win.isShow()) continue;
            if (win.draw(canvas, paint)) {
                invalidate();
            }
        }
        for (IconWindow win : mWindows) {
            if (!win.isShow()) continue;
            win.drawDragIcon(canvas, paint);
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
            mMenuBar.setShow(false);
        }

        if (mWindows[0] == null) {
            mWindows[0] = new IconWindow();
            mWindows[0].createWindow(0, 0, viewW, (viewH - 100)/2, Color.WHITE);
        }
        if (mWindows[1] == null) {
            mWindows[1] = new IconWindow();
            mWindows[1].createWindow(0, (viewH - 100)/2, viewW, (viewH - 100)/2, Color.LTGRAY);
        }
        for (IconWindow window : mWindows) {
            window.setWindows(mWindows);
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
        else if (IconWindoTouchEvent(viewTouch)) {
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
     * 各Windowのタッチ処理を変更する
     * @param vt
     * @return
     */
    private boolean IconWindoTouchEvent(ViewTouch vt) {
        for (Window win : mWindows) {
            if (!win.isShow()) continue;

            if (win.touchEvent(vt)) {
                return true;
            }
        }
        return false;
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
