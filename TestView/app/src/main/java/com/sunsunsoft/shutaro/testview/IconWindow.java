package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * アイコンのリストを表示するWindow
 */

public class IconWindow extends Window implements AutoMovable{
    enum viewState {
        none,
        drag,               // アイコンのドラッグ中
        icon_moving,        // アイコンの一変更後の移動中
    }
    public static final String TAG = "IconWindow";
    private static final int RECT_ICON_NUM = 10;
    private static final int CIRCLE_ICON_NUM = 10;

    private static final int ICON_W = 200;
    private static final int ICON_H = 150;

    private static final int MOVING_TIME = 10;


    // メンバ変数
    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // アイコン移動用
    private IconWindow[] windows;

    // ドラッグ中のアイコン
    private IconBase dragIcon;

    // アニメーション用
    private viewState state = viewState.none;

    private LinkedList<IconBase> icons = new LinkedList<IconBase>();

    // Get/Set
    public void setWindows(IconWindow[] windows) {
        this.windows = windows;
    }

    /**
     * 指定タイプのアイコンを追加
     * @param type
     * @return
     */
    public IconBase addIcon(IconShape type) {

        IconBase icon = null;
        switch (type) {
            case RECT:
                icon = new IconRect(0, 0, ICON_W, ICON_H);
                break;
            case CIRCLE:
            default:
                icon = new IconCircle(0, 0, ICON_H);
                break;
        }
        icons.add(icon);

        return icon;
    }

    /**
     * すでに作成済みのアイコンを追加
     * ※べつのWindowにアイコンを移動するのに使用する
     * @param icon
     * @return
     */
    public boolean addIcon(IconBase icon) {
        // すでに追加されている場合は追加しない
        if (!icons.contains(icon)) {
            icons.add(icon);
            return true;
        }
        return false;
    }

    /**
     * アイコンを削除
     * @param icon
     */
    public void removeIcon(IconBase icon) {
        icons.remove(icon);
    }

    /**
     * Windowを生成する
     * インスタンス生成後に一度だけ呼ぶ
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void createWindow(float x, float y, int width, int height, int bgColor) {
        super.createWindow(x, y, width, height, bgColor);

        // アイコンを追加
        for (int i=0; i<RECT_ICON_NUM; i++) {
            IconBase icon = addIcon(IconShape.RECT);
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
            IconBase icon = addIcon(IconShape.CIRCLE);
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
    }

    /**
     * 毎フレーム行う処理
     * @return true:描画を行う
     */
    public boolean doAction() {
        if (!isMoving) return false;
        // 移動処理
        if (isMoving) {
            if (move()) {
                isMoving = false;
            }
        }
        return true;
    }

    /**
     * 描画処理
     * @param canvas
     * @param paint
     * @return trueなら描画継続
     */
    public boolean draw(Canvas canvas, Paint paint) {
        if (!isShow) return false;

        boolean invalidate = false;

        // クリッピング領域を設定
        canvas.save();
        canvas.clipRect(rect);

        // 背景色
        paint.setColor(bgColor);
        canvas.drawRect(rect, paint);

        switch (state) {
            case none:
                for (IconBase icon : icons) {
                    if (icon == null) continue;
                    icon.draw(canvas, paint, getWin2ScreenPos(), rect);
                }
                break;
            case drag:
                for (IconBase icon : icons) {
                    if (icon == null || icon == dragIcon) continue;
                    icon.draw(canvas, paint, getWin2ScreenPos(), rect);
                }
                break;
        }

        // スクロールバー
        mScrollBar.draw(canvas, paint);

        if (state == viewState.icon_moving) {
            boolean allFinish = true;
            for (IconBase icon : icons) {
                if (icon == null || icon == dragIcon) continue;
                if (!icon.move()) {
                    allFinish = false;
                }
                icon.draw(canvas, paint, getWin2ScreenPos(), rect);
            }
            if (allFinish) {
                state = viewState.none;

            }
            invalidate = true;
        }
        // クリップ解除
        canvas.restore();

        return invalidate;
    }

    /**
     * ドラッグ中のアイコンを描画
     * @param canvas
     * @param paint
     */
    public void drawDragIcon(Canvas canvas, Paint paint) {
        if (dragIcon == null) return;

        // ドラッグアイコンは２つのWindowをまたいで表示されるのでクリップ外で描画
        if (state == viewState.drag) {
            dragIcon.draw(canvas, paint, getWin2ScreenPos(), null);
            return;
        }
    }

    /**
     * Windowのサイズを更新する
     * サイズ変更に合わせて中のアイコンを再配置する
     * @param width
     * @param height
     */
    @Override
    public void updateSize(int width, int height) {
        super.updateSize(width, height);
        // アイコンの整列
        sortRects(false);

        // スクロールバー
        mScrollBar.updateSize(width, height);
        mScrollBar.updateContent(contentSize);
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
            state = viewState.icon_moving;
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

        mScrollBar.updateContent(contentSize);
    }

    /**
     * アイコンをタッチする処理
     * @param vt
     * @return
     */
    private boolean touchIcons(ViewTouch vt) {
        for (IconBase icon : icons) {
            if (icon.checkTouch(toWinX(vt.touchX()), toWinY(vt.touchY()))) {
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
            if (icon.checkClick(toWinX(vt.touchX()), toWinY(vt.touchY()))) {
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
            if (icon.checkTouch(toWinX(vt.touchX()), toWinY(vt.touchY()))) {
                dragIcon = icon;
                ret = true;
                break;
            }
        }

        Collections.reverse(icons);

        if (ret) {
            state = viewState.drag;
            return true;
        }
        return ret;
    }

    private boolean dragMove(ViewTouch vt) {
        // ドラッグ中のアイコンを移動
        boolean ret = false;
        if (dragIcon != null) {
            dragIcon.move((int)vt.moveX, (int)vt.moveY);
            MyLog.print(TAG, "dragIcon:" + dragIcon.pos.x + " " + dragIcon.pos.y);
            ret = true;
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
     * @return trueならViewを再描画
     */
    private boolean dragEnd(ViewTouch vt) {
        // ドロップ処理
        // 他のアイコンの上にドロップされたらドロップ処理を呼び出す
        if (dragIcon == null) return false;
        boolean ret = false;

        boolean isDroped = false;

        // 全てのWindowの全ての
        for (IconWindow window : windows) {
            // Windowの領域外ならスキップ
            if (!(window.rect.contains(vt.getX(),vt.getY()))){
                continue;
            }

            LinkedList<IconBase> srcIcons = this.icons;
            LinkedList<IconBase> dstIcons = window.icons;

            // スクリーン座標系からWindow座標系に変換
            float winX = window.toWinX(vt.getX());
            float winY = window.toWinY(vt.getY());

            for (IconBase icon : dstIcons) {
                if (icon == dragIcon) continue;

                if (icon.checkDrop(winX, winY)) {
                    switch (icon.getShape()) {
                        case CIRCLE:
                            // ドラッグ位置のアイコンと場所を交換する
                            changeIcons(srcIcons, dstIcons, dragIcon, icon, window);
                            break;
                        case RECT:
                            // ドラッグ位置にアイコンを挿入する
                            insertIcons(srcIcons, dstIcons, dragIcon, icon, window);
                            break;
                        case IMAGE:
                            break;
                    }
                    isDroped = true;
                    break;
                }
            }

            // その他の場所にドロップされた場合
            if (!isDroped) {
                // 最後のアイコンの後の空きスペースにドロップされた場合
                if (dstIcons.size() > 0) {
                    IconBase lastIcon = dstIcons.getLast();
                    if ((lastIcon.getY() <= winY &&
                            winY <= lastIcon.getBottom() &&
                            lastIcon.getRight() <= winX) ||
                            (lastIcon.getBottom() <= winY))
                    {
                        // ドラッグ中のアイコンをリストの最後に移動
                        srcIcons.remove(dragIcon);
                        dstIcons.add(dragIcon);
                        isDroped = true;
                    }
                } else {
                    // ドラッグ中のアイコンをリストの最後に移動
                    srcIcons.remove(dragIcon);
                    dstIcons.add(dragIcon);
                }

                // 再配置
                if (srcIcons != dstIcons) {
                    // 座標系変換(移動元Windowから移動先Window)
                    if (isDroped) {
                        dragIcon.setPos(win1ToWin2X(dragIcon.pos.x, this, window), win1ToWin2Y(dragIcon.pos.y, this, window));
                    }

                    window.sortRects(true);
                }
                this.sortRects(true);

                isDroped = true;
            }
            if (isDroped) break;
        }

        dragIcon = null;
        return isDroped;
    }



    /**
     * タッチ処理
     * @param vt
     * @return trueならViewを再描画
     */
    public boolean touchEvent(ViewTouch vt) {
        if (!isShow) return false;
        if (state == viewState.icon_moving) return false;
        boolean done = false;

        // スクロールバーのタッチ処理
        if (mScrollBar.touchEvent(vt)) {
            contentTop.y = mScrollBar.getTopPos();
            return true;
        }

        // 範囲外なら除外
        if (!(rect.contains(vt.touchX(), vt.touchY()))) {
            return false;
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
                case Moving:
                    if (vt.isMoveStart()) {
                        if (dragStart(vt)) {
                            done = true;
                        }
                    }
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
                    break;
            }
        }

        if (!done) {
            // 画面のスクロール処理
            if (scrollView(vt)){
                done = true;
            }
        }
        return done;
    }

    /**
     * ２つのアイコンの位置を交換する
     * @param srcIcons
     * @param dstIcons
     * @param icon1
     * @param icon2
     * @param window
     */
    private void changeIcons(List<IconBase> srcIcons, List<IconBase> dstIcons, IconBase
            icon1, IconBase icon2, IconWindow window )
    {
        // アイコンの位置を交換
        // 並び順も重要！
        int index = dstIcons.indexOf(icon2);
        int index2 = srcIcons.indexOf(icon1);
        if (index == -1 || index2 == -1) return;

        srcIcons.remove(icon1);
        dstIcons.add(index, icon1);
        dstIcons.remove(icon2);
        srcIcons.add(index2, icon2);

        // 再配置
        if (srcIcons != dstIcons) {
            // ドロップアイコンの座標系を変換
            // アイコン1 Window -> アイコン2 Window
            dragIcon.setPos(dragIcon.pos.x + this.pos.x - window.pos.x,
                    dragIcon.pos.y + this.pos.y - window.pos.y);

            // アイコン2 Window -> アイコン1 Window
            icon2.setPos(icon2.pos.x + window.pos.x - this.pos.x,
                    icon2.pos.y + window.pos.y - this.pos.y);
            window.sortRects(true);
        }
        this.sortRects(true);
    }

    /**
     * アイコンを挿入する
     * @param srcIcons
     * @param dstIcons
     * @param srcIcon  挿入元のアイコン
     * @param dstIcon  挿入先のアイコン
     * @param window
     */
    private void insertIcons(List<IconBase> srcIcons, List<IconBase> dstIcons, IconBase srcIcon, IconBase dstIcon, IconWindow window)
    {
        int index = dstIcons.indexOf(dstIcon);
        if (index == -1) return;

        srcIcons.remove(srcIcon);
        dstIcons.add(index, srcIcon);

        // 再配置
        if (srcIcons != dstIcons) {
            // ドロップアイコンの座標系を変換
            dragIcon.setPos(srcIcon.pos.x + this.pos.x - window.pos.x,
                    srcIcon.pos.y + this.pos.y - window.pos.y);
            window.sortRects(true);
        }
        sortRects(true);
    }
}
