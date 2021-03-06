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
 * アイコンの表示と、ユーザーのタッチ操作でアイコンを移動
 */
public class MyView4 extends View implements OnTouchListener {
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

    // アイコンを動かす仕組み
    private IconBase dragIcon;
    private int dragX;
    private int dragY;

    // アニメーション用
    private viewState state = viewState.none;
    private int movingFrame;


    private Paint paint = new Paint();
    private TouchEventCallbacks _callbacks;
    private LinkedList<IconBase> icons = new LinkedList<IconBase>();

    public void setCallbacks(TouchEventCallbacks callbacks){
        _callbacks = callbacks;
    }

    public MyView4(Context context) {
        this(context, null);
    }

    public MyView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        for (int i=0; i<ICON_NUM; i++) {
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
                for (IconBase icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint);
                }
                break;
            case drag:
                for (IconBase icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint);
                }
                break;
            case icon_moving:
                boolean allFinish = true;
                for (IconBase icon : icons) {
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
            for (IconBase icon : icons) {
                int x = (i%column) * (ICON_W + 20);
                int y = (i/column) * (ICON_H + 20);
                icon.startMove(x,y,MOVING_TIME);
                i++;
            }
            state = viewState.icon_moving;
        }
        else {
            int i=0;
            for (IconBase icon : icons) {
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
        boolean touched = false;
        Collections.reverse(icons);
        for (IconBase icon : icons) {
            // 座標判定
            if (icon.pos.x <= x && x < icon.getRight() &&
                    icon.pos.y <= y && y < icon.getBottom())
            {
                // タッチされたアイコンを一番上(リストの最後)に持ってくる
                dragIcon = icon;
                touched = true;
                dragX = x;
                dragY = y;
                break;
            }
        }
        Collections.reverse(icons);
        // リストを反転した状態で追加するとおかしくなるので、反転を元に戻してから末尾に追加
        if (touched) {
            icons.remove(dragIcon);
            icons.add(dragIcon);
        }

//        StringBuffer str = new StringBuffer();
//        for (IconBase icon : icons) {
//            str.append(" " + icon.id);
//        }
//        Log.v("mylog", str.toString());

        invalidate();
    }

    private void touchMove(int x, int y) {
        // ドラッグ中のアイコンを移動
        if (dragIcon != null) {
            dragIcon.move(x - dragX, y - dragY);
            dragX = x;
            dragY = y;
        }
        invalidate();
    }

    private void touchUp(int x, int y) {
        dragIcon = null;
    }

    public boolean onTouch(View v, MotionEvent e) {
        boolean ret = false;

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
        // コールバック
        return ret;
    }
}
