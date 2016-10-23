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
 * アイコンの整列とアニメーション、挿入のテスト
 */
public class MyView5 extends View implements OnTouchListener {
    enum viewState {
        none,
        drag,               // アイコンのドラッグ中
        icon_moving,        // アイコンの一変更後の移動中
    }

    private static final int ICON_NUM = 15;
    private static final int ICON_W = 200;
    private static final int ICON_H = 150;
    private static final int MOVING_TIME = 10;
    private boolean firstDraw = false;
    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // アイコンを動かす仕組み
    private MyIcon dragIcon;
    private int dragX;
    private int dragY;

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

    public MyView5(Context context) {
        this(context, null);
    }

    public MyView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        for (int i=0; i<ICON_NUM; i++) {
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
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (firstDraw == false) {
            firstDraw = true;
            sortRects(false);
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
                    if (icon == null) continue;
                    icon.draw(canvas, paint);
                    ins.draw(canvas, paint);
                }
                break;
            case icon_moving:
                boolean allFinish = true;
                for (MyIcon icon : icons) {
                    if (icon == null) continue;
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
    }

    private void touchDown(int x, int y) {
        // タッチされたアイコンを選択する
        // 一番上のアイコンからタッチ判定したいのでリストを逆順（一番手前から）で参照する
        Collections.reverse(icons);
        for (MyIcon icon : icons) {
            // 座標判定
            if (icon.x <= x && x < icon.getRight() &&
                    icon.y <= y && y < icon.getBottom())
            {
                Log.v("mylog", "touchDown");
                dragIcon = icon;
                dragX = x;
                dragY = y;
                break;
            }
        }
        Collections.reverse(icons);

        state = viewState.drag;

        invalidate();
    }

    private void touchMove(int x, int y) {
        // ドラッグ中のアイコンを移動
        if (dragIcon != null) {
            dragIcon.move((int)viewTouch.moveX, (int)viewTouch.moveY);
        }
//        getInsertPosition(x,y);

        skipCount++;
        if (skipCount >= skipFrame) {
            invalidate();
            skipCount = 0;
        }
    }

    private void getInsertPosition(int x, int y) {
        // 挿入位置を求める
        // ドラッグ中のアイコンの下にあるアイコンの左半分に位置していたら左側、右半分に位置していたら右側
        // それ以外のスペースにドラッグしていたら最後のアイコンの右側
        boolean isOverlaped = false;

        for (MyIcon icon : icons) {
            // 座標判定
            if (dragIcon == icon) continue;
            if (icon.x <= x && x < icon.getRight() &&
                    icon.y <= y && y < icon.getBottom()) {
                if (x <= icon.getX() + icon.getWidth() / 2) {
                    // 左半分
                    ins.setRect(icon.x - 20, icon.y, 20, icon.getHeight());
                } else {
                    // 右半分
                    ins.setRect(icon.getRight(), icon.y, 20, icon.getHeight());
                }
                ins.isShow = true;
                isOverlaped = true;
                break;
            }
        }
        // どのアイコンの上でもない場合は最後のアイコンの次の右側に表示
        if (!isOverlaped) {
            MyIcon icon = icons.getLast();
            ins.setRect(icon.getRight(), icon.getY(), 20, icon.getHeight());
        }
    }

    private void touchUp() {
        dragIcon = null;
        state = viewState.none;
        ins.isShow = false;
    }

    public boolean onTouch(View v, MotionEvent e) {
        boolean ret = true;

        TouchType touchType = viewTouch.checkTouchType(e);

        switch(touchType) {
            case Click:

                break;
            case LongClick:

                break;
            case MoveStart:
                touchDown((int)e.getX(), (int)e.getY());
                break;
            case Moving:
                touchMove((int)e.getX(), (int)e.getY());
                break;
            case MoveEnd:
                touchUp();
                break;
        }
        /*
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG,"down");
                touchDown((int)e.getX(), (int)e.getY());

                // trueを返す。こうしないと以降のMoveイベントが発生しなくなる。
                ret = true;

                if (dragIcon != null) {
                    _callbacks.touchCallback(e.getAction());
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAG,"up");
                touchUp((int)e.getX(), (int)e.getY());
                ret = true;

                _callbacks.touchCallback(e.getAction());
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.v(TAG,"move");
                touchMove((int)e.getX(), (int)e.getY());
                ret = true;
                _callbacks.touchCallback(e.getAction());
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
        */
        // コールバック
        return ret;
    }
}
