package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

/**
 * メニューバー、サブViewのサンプル

 */
public class MyView11 extends View implements OnTouchListener, MenuItemCallbacks, IconCallbacks{
    enum WindowType {
        Icon1,
        Icon2,
        MenuBar,
        Log
    }

    public static final String TAG = "MyView11";

    // Windows
    private Window[] mWindows = new Window[WindowType.values().length];
    // IconWindow
    private IconWindow[] mIconWindows = new IconWindow[2];

    // MessageWindow
    private LogWindow mLogWin;

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
        for (IconWindow win : mIconWindows) {
            win.updateSize(width, height);
        }
        invalidate();
    }

    public void updateWindowPos(float x, float y){
        for (IconWindow win : mIconWindows) {
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
        mIconWindows[0].updateSize(width, height);
        mIconWindows[1].setShow(false);
        invalidate();
    }

    /**
     * IconWindowを２つ表示
     * @param width
     * @param height
     */
    public void updateShow2(int width, int height) {
        mIconWindows[0].setShow(true);
        mIconWindows[0].updateSize(width, (height - 100)/2);
        mIconWindows[1].setShow(true);
        mIconWindows[1].updateSize(width, (height - 100)/2);
        invalidate();
    }

    public void moveTest1(){
        mIconWindows[1].startMove(0, getHeight(), 15);
        invalidate();
    }

    public void moveTest2(){
        mIconWindows[1].startMove(0, (getHeight() - 100) / 2, 15);
        invalidate();
    }

    public void showText() {
        mLogWin.addMessage("hoge", Color.WHITE);
        invalidate();
    }


    public MyView11(Context context) {
        this(context, null);
    }

    public MyView11(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
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

        // IconWindow
        if (mIconWindows[0] == null) {
            mIconWindows[0] = IconWindow.createInstance(this, this, 0, 0, viewW, (viewH - 100)/2, Color.WHITE);
            mIconWindows[0].setWindows(mIconWindows);
            mWindows[WindowType.Icon1.ordinal()] = mIconWindows[0];
        }

        if (mIconWindows[1] == null) {
            mIconWindows[1] = IconWindow.createInstance(this, this, 0, (viewH - 100)/2, viewW, (viewH - 100)/2, Color.LTGRAY);
            mIconWindows[1].setWindows(mIconWindows);
            mWindows[WindowType.Icon2.ordinal()] = mIconWindows[1];
        }
        // メニューバー
        if (mMenuBar == null) {
            mMenuBar = MenuBar.createInstance(this, this, viewW, viewH, Color.BLACK);
            mWindows[WindowType.MenuBar.ordinal()] = mMenuBar;
        }

        // LogWindow
        if (mLogWin == null) {
            mLogWin = LogWindow.createInstance(getContext(), this,
                    viewW / 2, viewH,
                    Color.argb(128,0,0,0));
            mWindows[WindowType.Log.ordinal()] = mLogWin;
        }

        if (resetSize) {
            int width = MeasureSpec.EXACTLY | newWidth;
            int height = MeasureSpec.EXACTLY | newHeight;
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);

        // アイコンWindow
        // アクション(手前から順に処理する)
        for (int i=mWindows.length - 1; i >= 0; i--) {
            Window win = mWindows[i];
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
        for (IconWindow win : mIconWindows) {
            if (!win.isShow()) continue;
            win.drawDragIcon(canvas, paint);
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
        if (WindoTouchEvent(viewTouch)) {
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
    private boolean WindoTouchEvent(ViewTouch vt) {
        // 手前から順に処理する
        for (int i=mWindows.length - 1; i >= 0; i--) {
            Window win = mWindows[i];
            if (!win.isShow()) continue;

            if (win.touchEvent(vt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * メニューアイテムをタップしてアイコンを追加する
     * @param windowId
     * @param shape
     * @param menuItemId
     */
    private void addIcon(int windowId, IconShape shape, MenuItemId menuItemId) {
        IconWindow iconWindow = mIconWindows[windowId];
        IconManager manager = iconWindow.getIconManager();
        IconBase icon = manager.addIcon(shape, AddPos.Top);

        // アイコンの初期座標は追加メニューアイコンの位置
        PointF menuPos = mMenuBar.getItemPos(menuItemId);
        icon.setPos(iconWindow.toWinX(menuPos.x), iconWindow.toWinY(menuPos.y));
        mIconWindows[windowId].sortRects(true);
    }

    /**
     * MenuItemCallbacks
     */
    /**
     * メニューアイテムをタップした時のコールバック
     */
    public void callback1(MenuItemId id) {
        switch (id) {
            case AddTop:
                break;
            case AddCard:
                addIcon(0, IconShape.CIRCLE, id);
                break;
            case AddBook:
                addIcon(0, IconShape.RECT, id);
                break;
            case AddBox:
                break;
            case SortTop:
                break;
            case Sort1:
                break;
            case Sort2:
                break;
            case Sort3:
                break;
            case ListTypeTop:
                break;
            case ListType1:
                break;
            case ListType2:
                break;
            case ListType3:
                break;
        }
        MyLog.print(TAG, "menu item clicked " + id);
    }

    public void callback2() {
        MyLog.print(TAG, "menu item moved");
    }

    /**
     * IconCallbacks
     */
    public void clickIcon(IconBase icon) {
        MyLog.print(TAG, "clickIcon");
        switch(icon.shape) {
            case CIRCLE:
                break;
            case RECT:
                break;
            case BOX: {
                // 配下のアイコンをSubWindowに表示する
                if (icon instanceof IconBox) {
                    IconBox box = (IconBox)icon;
                    mIconWindows[1].setIconManager(box.getIconManager());
                    mIconWindows[1].sortRects(false);
                    box.setSubWindow(mIconWindows[1]);
                }
            }
                break;
            case IMAGE:
                break;
        }
    }
    public void longClickIcon(IconBase icon) {
        MyLog.print(TAG, "longClickIcon");
    }
    public void dropToIcon(IconBase icon) {
        MyLog.print(TAG, "dropToIcon");
    }

}
