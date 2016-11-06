package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.LinkedList;


/**
 * スクロールを自前で管理するView
 * コンテンツ全体のサイズを自前で管理して、画面に表示するViewのサイズよりも大きかったらスクロールできるようにする。
 */
public class MyView8 extends View implements OnTouchListener {
    enum viewState {
        none,
        drag,               // アイコンのドラッグ中
        icon_moving,        // アイコンの一変更後の移動中
    }

    private static final int RECT_ICON_NUM = 30;
    private static final int CIRCLE_ICON_NUM = 30;
    private static final int ICON_W = 200;
    private static final int ICON_H = 150;
    private static final int MOVING_TIME = 10;
    private boolean firstDraw = false;
    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // スクロール用
    private Size contentSize = new Size();  // 領域全体のサイズ
    private PointF contentTop = new PointF();  // 画面に表示する領域の左上の座標
    MyScrollBar mScrollV;
    MyScrollBar mDragScrollBar;   // todo

    // サイズ更新用
    private boolean resetSize;
    private int newWidth, newHeight;

    // アイコンを動かす仕組み
    private IconBase dragIcon;

    // クリック判定の仕組み
    private ViewTouch viewTouch = new ViewTouch();

    // アニメーション用
    private viewState state = viewState.none;

    private Paint paint = new Paint();
    private LinkedList<IconBase> icons = new LinkedList<IconBase>();

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
        if (mScrollV != null) {
            mScrollV.updateContent(contentSize);
        }
        setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    public void setContentSize(int width, int height) {
        contentSize.width = width;
        contentSize.height = height;
    }

    public MyView8(Context context) {
        this(context, null);
    }

    public MyView8(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        // アイコンを追加
        for (int i=0; i<RECT_ICON_NUM; i++) {
            IconBase icon = new IconRect(null, 0, 0, ICON_W, ICON_H);
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
            IconBase icon = new IconCircle(null, 0, 0, ICON_H);
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
        // 背景塗りつぶし
        canvas.drawColor(Color.WHITE);

        // アンチエリアシング(境界のぼかし)
        paint.setAntiAlias(true);

        switch (state) {
            case none:
                for (IconBase icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint, contentTop, null);
                }
                break;
            case drag:
                for (IconBase icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    icon.draw(canvas, paint, contentTop, null);
                }
                if (dragIcon != null) {
                    dragIcon.draw(canvas, paint, contentTop, null);
                }
                break;
            case icon_moving:
                boolean allFinish = true;
                for (IconBase icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    if (!icon.move()) {
                        allFinish = false;
                    }
                    icon.draw(canvas, paint, contentTop, null);
                }
                if (allFinish) {
                    state = viewState.none;
                } else {
                    invalidate();
                }
                break;
        }

        // スクロールバー
        mScrollV.draw(canvas, paint);
    }

    /**
     * Viewのサイズを指定する
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        sortRects(false, MeasureSpec.getSize(widthMeasureSpec));

        int viewW = MeasureSpec.getSize(widthMeasureSpec);
        int viewH = MeasureSpec.getSize(heightMeasureSpec);

        if (mScrollV == null) {
            mScrollV = new MyScrollBar(ScrollBarType.Right, ScrollBarInOut.In, new PointF(), viewW, viewH, 40, contentSize.height);
        } else {
            mScrollV.updateContent(contentSize);
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
     * アイコンを整列する
     * Viewのサイズが確定した時点で呼び出す
     */
    public void sortRects(boolean animate) {
        sortRects(animate, 0);
    }

    public void sortRects(boolean animate, int width) {
        if (width == 0) {
            width = getWidth();
        }
        int column = width / (ICON_W + 20);
        if (column <= 0) {
            return;
        }

        int maxHeight = 0;
        if (animate) {
            int i=0;
            for (IconBase icon : icons) {
                int x = (i%column) * (ICON_W + 20);
                int y = (i/column) * (ICON_H + 20);
                int height = y + (ICON_H + 20);
                if ( height >= maxHeight ) {
                    maxHeight = height;
                }
                icon.startMove(x,y,MOVING_TIME);
                i++;
            }
            state = viewState.icon_moving;
            invalidate();
        }
        else {
            int i=0;
            for (IconBase icon : icons) {
                int x = (i%column) * (ICON_W + 20);
                int y = (i/column) * (ICON_H + 20);
                int height = y + (ICON_H + 20);
                if ( height >= maxHeight ) {
                    maxHeight = height;
                }
                icon.setPos(x, y);
                i++;
            }
        }

        setContentSize(width, maxHeight);
    }

    /**
     * コンテンツ表示領域を計算する
     */
    private void setContentArea() {
        //　全アイコンが収まるサイズを求める
        int w = 0, h = 0;
        Collections.reverse(icons);

        for (IconBase icon : icons) {
            if (icon.getRight() > w) {
                w = (int)icon.getRight();
            }
            if (icon.getBottom() > h) {
                h = (int)icon.getBottom();
            }
        }

        Collections.reverse(icons);
    }


    /**
     * アイコンをタッチする処理
     * @param vt
     * @return
     */
    private boolean touchIcons(ViewTouch vt) {
        for (IconBase icon : icons) {
            if (icon.checkClick(vt.touchX(), vt.touchY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * アイコンをクリックする処理
     * @param vt
     * @return アイコンがクリックされたらtrue
     */
    private boolean clickIcons(ViewTouch vt) {
        // どのアイコンがクリックされたかを判定
        for (IconBase icon : icons) {
            if (icon.checkClick(vt.touchX(), vt.touchY())) {
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
        for (IconBase icon : icons) {
            // 座標判定
            if (icon.checkTouch(vt.touchX(), vt.touchY())) {
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
        } else if (mDragScrollBar != null) {
//            mDragScrollBar.move(vt.moveX, vt.moveY);
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
        for (IconBase icon : icons) {
            if (icon == dragIcon) continue;
            if (icon.checkDrop(vt.touchX(), vt.touchY())) {
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
            IconBase lastIcon = icons.getLast();
            if ((lastIcon.getY() <= vt.touchY() && vt.touchY() <= lastIcon.getBottom() &&
                    lastIcon.getRight() <= vt.touchX()) ||
                    (lastIcon.getBottom() <= vt.touchY()))
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


    /**
     * Viewをスクロールする処理
     * @param tv
     * @return
     */
    private boolean scrollView(ViewTouch tv) {
        // タッチの移動とスクロール方向は逆
        float moveX = tv.moveX * (-1);
        float moveY = tv.moveY * (-1);

        // 横
        if (getWidth() < contentSize.width) {
            contentTop.x += moveX;
            if (contentTop.x < 0) {
                contentTop.x = 0;
            } else if (contentTop.x + getWidth() > contentSize.width) {
                contentTop.x = contentSize.width - getWidth();
            }
        }

        // 縦
        if (getHeight() < contentSize.height) {
            contentTop.y += moveY;
            if (contentTop.y < 0) {
                contentTop.y = 0;
            } else if (contentTop.y + getHeight() > contentSize.height) {
                contentTop.y = contentSize.height - getHeight();
            }
        }
        // スクロールバーの表示を更新
        mScrollV.updateScroll(contentTop);

        invalidate();

        return true;
    }

    /**
     * タッチイベント処理
     * @param v
     * @param e
     * @return
     */
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
            case Touch:
                if (touchIcons(viewTouch)) {
                    done = true;
                }
                break;
            case Click:
                if (clickIcons(viewTouch)) {
                    done = true;
                }
                break;
            case LongClick:
                longClickIcons(viewTouch);
                done = true;
                break;
            case Moving:
                if (viewTouch.isMoveStart()) {
                    if (dragStart(viewTouch)) {
                        done = true;
                    }
                }

                if (dragMove(viewTouch)) {
                    done = true;
                } else if (scrollView(viewTouch)){
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
}
