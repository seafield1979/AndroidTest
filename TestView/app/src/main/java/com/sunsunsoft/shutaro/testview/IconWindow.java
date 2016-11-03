package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

import java.util.Collections;
import java.util.LinkedList;

/**
 * アイコンのリストを表示するWindow
 */

public class IconWindow {
    PointF pos = new PointF();
    Size size = new Size();


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

    // アイコンを動かす仕組み
    private IconBase dragIcon;

    // アニメーション用
    private MyView10.viewState state = MyView10.viewState.none;

    private LinkedList<IconBase> icons = new LinkedList<IconBase>();


    public void createWindow(int width, int height) {
        size.width = width;
        size.height = height;

        // アイコンを追加
        for (int i=0; i<RECT_ICON_NUM; i++) {
            IconBase icon = new IconRect(0, 0, ICON_W, ICON_H);
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
            IconBase icon = new IconCircle(0, 0, ICON_H);
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
        sortRects(false);

        mScrollV = new MyScrollBar(ScrollBarType.Right, width, height, 50, contentSize.height);
        mScrollV.updateContent(contentSize, width, height);
    }

    public void setContentSize(int width, int height) {
        contentSize.width = width;
        contentSize.height = height;
    }

    public boolean draw(Canvas canvas, Paint paint) {

        switch (state) {
            case none:
                for (IconBase icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint, contentTop);
                }
                break;
            case drag:
                for (IconBase icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    icon.draw(canvas, paint, contentTop);
                }
                if (dragIcon != null) {
                    dragIcon.draw(canvas, paint, contentTop);
                }
                break;
            case icon_moving:
                boolean allFinish = true;
                for (IconBase icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    if (!icon.move()) {
                        allFinish = false;
                    }
                    icon.draw(canvas, paint, contentTop);
                }
                if (allFinish) {
                    state = MyView10.viewState.none;
                } else {
                    return true;
                }
                break;
        }

        // スクロールバー
        mScrollV.draw(canvas, paint);

        return false;
    }

    public void setSize(int width, int height) {
        // アイコンの整列
        sortRects(false);

        // スクロールバー
        mScrollV.updateContent(contentSize, width, height);

    }

    /**
     * アイコンを整列する
     * Viewのサイズが確定した時点で呼び出す
     */
    public void sortRects(boolean animate) {
        int column = size.width / (ICON_W + 20);
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
            state = MyView10.viewState.icon_moving;
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

        setContentSize(size.width, maxHeight);
    }

    /**
     * アイコンをタッチする処理
     * @param vt
     * @return
     */
    private boolean touchIcons(ViewTouch vt) {
        for (IconBase icon : icons) {
            if (icon.checkTouch(vt.touchX(), vt.touchY())) {
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
            state = MyView10.viewState.drag;
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
            if (icon.checkDrop(vt.touchX(), vt.touchX())) {
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
     * Viewの空きスペースをドラッグすると表示領域をスクロールすることができる
     * @param tv
     * @return
     */
    private boolean scrollView(ViewTouch tv) {
        // タッチの移動とスクロール方向は逆
        float moveX = tv.moveX * (-1);
        float moveY = tv.moveY * (-1);

        // 横
        if (size.width < contentSize.width) {
            contentTop.x += moveX;
            if (contentTop.x < 0) {
                contentTop.x = 0;
            } else if (contentTop.x + size.width > contentSize.width) {
                contentTop.x = contentSize.width - size.width;
            }
        }

        // 縦
        if (size.height < contentSize.height) {
            contentTop.y += moveY;
            if (contentTop.y < 0) {
                contentTop.y = 0;
            } else if (contentTop.y + size.height > contentSize.height) {
                contentTop.y = contentSize.height - size.height;
            }
        }
        // スクロールバーの表示を更新
        mScrollV.updateScroll(contentTop);

        return true;
    }

    /**
     * タッチ処理
     * @param vt
     * @return trueならViewを再描画
     */
    public boolean touchEvent(ViewTouch vt) {
        boolean done = false;
        boolean invalidate = false;

        // スクロールバーのタッチ処理
        if (mScrollV.touchEvent(vt)) {
            contentTop.y = mScrollV.getTopPos();
            invalidate = true;
            done = true;
        }

        if (!done) {
            switch (vt.type) {
                case Touch:
                    if (touchIcons(vt)) {
                        done = true;
                    }
                    break;
                case Click:
                    if (clickIcons(vt)) {
                        done = true;
                    }
                    break;
                case LongClick:
                    longClickIcons(vt);
                    done = true;
                    break;
                case MoveStart:
                    if (dragStart(vt)) {
                        done = true;
                    }
                    break;
                case Moving:
                    if (dragMove(vt)) {
                        done = true;
                    }
                    break;
                case MoveEnd:
                    if (dragEnd(vt)) {
                        done = true;
                    }
                    break;
                case MoveCancel:
                    sortRects(false);
                    dragIcon = null;
                    invalidate = true;
                    break;
            }
        }

        if (!done) {
            // 画面のスクロール処理
            if (scrollView(vt)){
                done = true;
                invalidate = true;
            }
        }
        return invalidate;
    }
}
