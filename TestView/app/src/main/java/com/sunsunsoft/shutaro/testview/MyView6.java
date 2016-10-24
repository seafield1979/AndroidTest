package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Collections;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;


/**
 * アイコン表示領域のスクロールテスト
 */
public class MyView6 extends View implements OnTouchListener {
    enum viewState {
        none,
        drag,               // アイコンのドラッグ中
        icon_moving,        // アイコンの一変更後の移動中
    }

    private static final int RECT_ICON_NUM = 10;
    private static final int CIRCLE_ICON_NUM = 10;
    private static final int ICON_W = 200;
    private static final int ICON_H = 150;
    private static final int MOVING_TIME = 10;
    private boolean firstDraw = false;
    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // スクロールバー
    private MyScrollBar scrollBar;

    // アイコン表示領域
    private int contentWidth, contentHeight;
    private float scrollX, scrollY;

    // アイコンを動かす仕組み
    private MyIcon dragIcon;

    // クリック判定の仕組み
    private ViewTouch viewTouch = new ViewTouch();

    // アニメーション用
    private viewState state = viewState.none;

    // 挿入位置
    private InsertPoint ins = new InsertPoint(0,0,0,0);


    private Paint paint = new Paint();
    private TouchEventCallbacks _callbacks;
    private LinkedList<MyIcon> icons = new LinkedList<MyIcon>();

    public void setCallbacks(TouchEventCallbacks callbacks){
        _callbacks = callbacks;
    }

    public MyView6(Context context) {
        this(context, null);
    }

    public MyView6(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        // アイコンを追加
        for (int i=0; i<RECT_ICON_NUM; i++) {
            MyIcon icon = new MyIconRect(0, 0, ICON_W, ICON_H);
            icons.add(icon);
            int color = 0;
            switch (i%3) {
                case 0:
                    color = Color.rgb(255,0,0);
                    break;
                case 1:
                    color = Color.rgb(0,255,0);
                    break;
                case 2:
                    color = Color.rgb(0,0,255);
                    break;
            }
            icon.setColor(color);
        }

        for (int i=0; i<CIRCLE_ICON_NUM; i++) {
            MyIcon icon = new MyIconCircle(0, 0, ICON_H);
            icons.add(icon);
            int color = 0;
            switch (i%3) {
                case 0:
                    color = Color.rgb(255,0,0);
                    break;
                case 1:
                    color = Color.rgb(0,255,0);
                    break;
                case 2:
                    color = Color.rgb(0,0,255);
                    break;
            }
            icon.setColor(color);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (firstDraw == false) {
            firstDraw = true;
            sortRects(false);
            initScrollBar();
        }

        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);

        switch (state) {
            case none:
                for (MyIcon icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint);
                }
                break;
            case drag:
                for (MyIcon icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    icon.draw(canvas, paint);
                    ins.draw(canvas, paint);
                }
                if (dragIcon != null) {
                    dragIcon.draw(canvas, paint);
                }
                break;
            case icon_moving:
                boolean allFinish = true;
                for (MyIcon icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    if (!icon.move()) {
                        allFinish = false;
                    }
                    icon.draw(canvas, paint);
                }
                if (allFinish) {
                    state = viewState.none;
                } else {
                    invalidate();
                }
                break;
        }

        // スクロールバー
        scrollBar.draw(canvas, paint);
    }

    private void initScrollBar() {
        if (scrollBar == null) {
            scrollBar = new MyScrollBar(ScrollBarType.Vertical,
                    getWidth() - 200, 0,
                    getHeight(), 100,
                    1000, 300);
        }
    }

    /**
     * アイコンを整列する
     * Viewのサイズが確定した時点で呼び出す
     */
    public void sortRects(boolean animate) {
        int column = this.getWidth() / (ICON_W + 20);
        if (column <= 0) {
            return;
        }

        if (animate) {
            int i=0;
            for (MyIcon icon : icons) {
                int x = (i%column) * (ICON_W + 20);
                int y = (i/column) * (ICON_H + 20);
                icon.startMove(x,y,MOVING_TIME);
                i++;
            }
            state = viewState.icon_moving;
            invalidate();
        }
        else {
            int i=0;
            for (MyIcon icon : icons) {
                int x = (i%column) * (ICON_W + 20);
                int y = (i/column) * (ICON_H + 20);
                icon.setPos(x, y);
                i++;
            }
        }

        setContentArea();
    }

    /**
     * コンテンツ表示領域を計算する
     */
    private void setContentArea() {
        //　全アイコンが収まるサイズを求める
        int w = 0, h = 0;
        Collections.reverse(icons);

        for (MyIcon icon : icons) {
            if (icon.getRight() > w) {
                w = (int)icon.getRight();
            }
            if (icon.getBottom() > h) {
                h = (int)icon.getBottom();
            }
        }

        Collections.reverse(icons);

        contentWidth = w;
        contentHeight = h;
    }

    /**
     * アイコンをクリックする処理
     * @param vt
     * @return アイコンがクリックされたらtrue
     */
    private boolean clickIcons(ViewTouch vt) {
        // どのアイコンがクリックされたかを判定
        for (MyIcon icon : icons) {
            if (icon.checkClick(vt.touchX, vt.touchY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * アイコンをロングクリックする処理
     * @param vt
     */
    private void longClickIcons(ViewTouch vt) {

    }

    /**
     * アイコンをドラッグ開始
     * @param vt
     */
    private boolean dragStart(ViewTouch vt) {
        // タッチされたアイコンを選択する
        // 一番上のアイコンからタッチ判定したいのでリストを逆順（一番手前から）で参照する
        boolean ret = false;
        Collections.reverse(icons);
        for (MyIcon icon : icons) {
            // 座標判定
            if (icon.x <= vt.touchX && vt.touchX < icon.getRight() &&
                    icon.y <= vt.touchY && vt.touchY < icon.getBottom())
            {
                dragIcon = icon;
                ret = true;
                break;
            }
        }
        Collections.reverse(icons);

        if (ret) {
            state = viewState.drag;
            invalidate();
            return true;
        }
        return ret;
    }

    private boolean dragMove(ViewTouch vt) {
        // ドラッグ中のアイコンを移動
        boolean ret = false;
        if (dragIcon != null) {
            dragIcon.move((int)vt.moveX, (int)vt.moveY);
            ret = true;
        }

        skipCount++;
        if (skipCount >= skipFrame) {
            invalidate();
            skipCount = 0;
        }
        return ret;
    }

    /**
     * ドラッグ終了時の処理
     * @param vt
     * @return
     */
    private boolean dragEnd(ViewTouch vt) {
        // ドロップ処理
        // 他のアイコンの上にドロップされたらドロップ処理を呼び出す
        if (dragIcon == null) return false;
        boolean ret = false;

        boolean isDroped = false;
        for (MyIcon icon : icons) {
            if (icon == dragIcon) continue;
            if (icon.checkDrop(vt.x, vt.y)) {
                switch(icon.getShape()) {
                    case CIRCLE:
                        // ドラッグ位置のアイコンと場所を交換する
                    {
                        int index = icons.indexOf(icon);
                        int index2 = icons.indexOf(dragIcon);
                        icons.remove(dragIcon);
                        icons.add(index, dragIcon);
                        icons.remove(icon);
                        icons.add(index2, icon);

                        // 再配置
                        sortRects(true);
                    }
                    break;
                    case RECT:
                        // ドラッグ位置にアイコンを挿入する
                    {
                        int index = icons.indexOf(icon);
                        icons.remove(dragIcon);
                        icons.add(index, dragIcon);

                        // 再配置
                        sortRects(true);
                    }
                    break;
                    case IMAGE:
                        break;
                }
                isDroped = true;
                ret = true;
                break;
            }
        }

        // その他の場所にドロップされた場合
        if (!isDroped) {
            // 最後のアイコンの後の空きスペースにドロップされた場合
            MyIcon lastIcon = icons.getLast();
            if ((lastIcon.getY() <= vt.y && vt.y <= lastIcon.getBottom() &&
                    lastIcon.getRight() <= vt.x) ||
                    (lastIcon.getBottom() <= vt.y))
            {
                // ドラッグ中のアイコンをリストの最後に移動
                icons.remove(dragIcon);
                icons.add(dragIcon);
            }

            // 再配置
            sortRects(true);
        }

        dragIcon = null;
        return ret;
    }

    public boolean onTouch(View v, MotionEvent e) {
        boolean ret = true;

        if (state == viewState.icon_moving) return true;

        TouchType touchType = viewTouch.checkTouchType(e);

        if (viewTouch.checkLongTouch()) {
            // ロングタッチの処理
            Log.v("view5", "Long Touch");
        }

        boolean done = false;

        switch(touchType) {
            case Click:
                if (clickIcons(viewTouch)) {
                    done = true;
                }
                break;
            case LongClick:
                longClickIcons(viewTouch);
                done = true;
                break;
            case MoveStart:
                if (dragStart(viewTouch)) {
                    done = true;
                }
                break;
            case Moving:
                if (dragMove(viewTouch)) {
                    done = true;
                }
                break;
            case MoveEnd:
                if (dragEnd(viewTouch)) {
                    done = true;
                }
                break;
            case MoveCancel:
                sortRects(false);
                dragIcon = null;
                invalidate();
                break;
        }
        if (done) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
        }

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // trueを返す。こうしないと以降のMoveイベントが発生しなくなる。
                ret = true;

                // ScrollViewのスクロールを停止
                if (e.getX() < v.getWidth() / 2) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:
                ret = true;
                if (e.getX() < v.getWidth() / 2) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            default:
        }

        // コールバック
        return ret;
    }
}
