package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

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

    // アイコンウィンドウのメイン、サブ
    // ホームはトップのウィンドウ
    // サブはトップウィンドウ以下のBoxアイコンを開いた時のウィンドウ
    enum WindowType {
        Home,
        Sub
    }

    public static final String TAG = "IconWindow";
    private static final int RECT_ICON_NUM = 10;
    private static final int CIRCLE_ICON_NUM = 10;
    private static final int BOX_ICON_NUM = 10;

    private static final int ICON_W = 200;
    private static final int ICON_H = 150;

    private static final int MOVING_TIME = 10;

    // ホームWindowを作成したかどうか
    // ホームWindowは１つしか存在できないため、最初のインスタンスはホーム、それ以降はサブになる
    private static boolean createdHome;

    // メンバ変数
    private WindowType type;
    private View mParentView;
    private IconCallbacks mIconCallbacks;
    private IconManager mIconManager;

    // 他のIconWindow
    // ドラッグで他のWindowにアイコンを移動するのに使用する
    private IconWindow[] windows;

    // ドラッグ中のアイコン
    private IconBase dragIcon;
    // ドロップ中のアイコン
    private IconBase dropIcon;

    // アニメーション用
    private viewState state = viewState.none;

    // Iconのアニメーション中
    private boolean isAnimating;

    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // Get/Set
    public WindowType getType() {
        return type;
    }

    public void setType(WindowType type) {
        this.type = type;
    }
    public IconManager getIconManager() {
        return mIconManager;
    }
    public void setIconManager(IconManager mIconManager) {
        this.mIconManager = mIconManager;
    }

    public LinkedList<IconBase> getIcons() {
        if (mIconManager == null) return null;
        return mIconManager.getIcons();
    }

    public void setWindows(IconWindow[] windows) {
        this.windows = windows;
    }

    public void setParentView(View mParentView) {
        this.mParentView = mParentView;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public IconCallbacks getIconCallbacks() {
        return mIconCallbacks;
    }

    /**
     * インスタンスを生成する
     * Homeタイプが２つできないように自動でHome、Subのタイプ分けがされる
     * @return
     */
    public static IconWindow createInstance(View parent, IconCallbacks iconCallbacks, float x, float y, int width, int height, int bgColor) {
        IconWindow instance = new IconWindow();
        if (!createdHome) {
            createdHome = true;
            instance.type = WindowType.Home;
            instance.mIconManager = IconManager.createInstance(parent, instance);
        } else {
            instance.type = WindowType.Sub;
        }
        instance.mIconCallbacks = iconCallbacks;
        instance.createWindow(parent, x, y, width, height, bgColor);
        return instance;
    }

    /**
     * Windowを生成する
     * インスタンス生成後に一度だけ呼ぶ
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void createWindow(View parent, float x, float y, int width, int height, int bgColor) {
        super.createWindow(x, y, width, height, bgColor);

        mParentView = parent;

        // アイコンを追加
        if (type == WindowType.Home) {
            for (int i = 0; i < RECT_ICON_NUM; i++) {

                IconBase icon = mIconManager.addIcon(IconShape.RECT, AddPos.Tail);
                int color = 0;
                switch (i % 3) {
                    case 0:
                        color = Color.rgb(255, 0, 0);
                        break;
                    case 1:
                        color = Color.rgb(0, 255, 0);
                        break;
                    case 2:
                        color = Color.rgb(0, 0, 255);
                        break;
                }
                icon.setColor(color);
            }

            for (int i = 0; i < CIRCLE_ICON_NUM; i++) {
                IconBase icon = mIconManager.addIcon(IconShape.CIRCLE, AddPos.Tail);
                int color = 0;
                switch (i % 3) {
                    case 0:
                        color = Color.rgb(255, 0, 0);
                        break;
                    case 1:
                        color = Color.rgb(0, 255, 0);
                        break;
                    case 2:
                        color = Color.rgb(0, 0, 255);
                        break;
                }
                icon.setColor(color);
            }
            for (int i = 0; i < BOX_ICON_NUM; i++) {
                IconBase icon = mIconManager.addIcon(IconShape.BOX, AddPos.Tail);
            }
        }

        sortRects(false);
    }

    /**
     * 毎フレーム行う処理
     * @return true:描画を行う
     */
    public boolean doAction() {
        boolean isDraw = false;
        if (isMoving) {
            // 移動処理
            if (isMoving) {
                if (move()) {
                    isMoving = false;
                } else {
                    isDraw = true;
                }
            }
        }
        if (isAnimating) {
            boolean allFinished = true;
            List<IconBase> icons = getIcons();
            for (IconBase icon : icons) {
                if (icon.animate()) {
                    isDraw = true;
                    allFinished = false;
                }
            }
            if (allFinished) {
                isAnimating = false;
            }
        }
        return isDraw;
    }

    /**
     * 描画処理
     * @param canvas
     * @param paint
     * @return trueなら描画継続
     */
    public boolean draw(Canvas canvas, Paint paint) {
        if (!isShow) return false;
        List<IconBase> icons = getIcons();
        if (icons == null) return false;

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
        List<IconBase> icons = getIcons();
        if (icons == null) return;

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

        mParentView.invalidate();
    }

    /**
     * アイコンをタッチする処理
     * @param vt
     * @return
     */
    private boolean touchIcons(ViewTouch vt) {
        List<IconBase> icons = getIcons();
        if (icons == null) return false;

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
        List<IconBase> icons = getIcons();
        if (icons == null) return false;

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
        List<IconBase> icons = getIcons();
        if (icons == null) return false;

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
            ret = true;

            // ドラッグ中のアイコンが別のアイコンの上にあるかをチェック
            int dragX = (int) toWinX(vt.getX());
            int dragY = (int) toWinY(vt.getY());

            boolean isDone = false;

            // 現在のドロップフラグをクリア
            if (dropIcon != null) {
                dropIcon.isDroping = false;
            }

            for (IconWindow window : windows) {
                List<IconBase> icons = window.getIcons();
                if (icons == null) continue;
                for (IconBase icon : icons) {
                    if (icon == dragIcon) continue;
                    if (icon.getRect().contains(dragX, dragY)) {
                        isDone = true;
                        dropIcon = icon;
                        dropIcon.isDroping = true;
                        break;
                    }
                }
                if (isDone) break;
            }
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

        if (dropIcon != null) {
            dropIcon.isDroping = false;
            dropIcon = null;
        }

        // 全てのWindowの全ての
        for (IconWindow window : windows) {
            // Windowの領域外ならスキップ
            if (!(window.rect.contains(vt.getX(),vt.getY()))){
                continue;
            }

            LinkedList<IconBase> srcIcons = getIcons();
            LinkedList<IconBase> dstIcons = window.getIcons();

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
                            isDroped = true;
                            break;
                        case RECT:
                        case IMAGE:
                            // ドラッグ位置にアイコンを挿入する
                            insertIcons(srcIcons, dstIcons, dragIcon, icon, window, true);
                            isDroped = true;
                            break;
                        case BOX:
                            if (dragIcon.shape != IconShape.BOX) {
                                IconBox box = (IconBox) icon;
                                if (box.getIcons() != null) {
                                    moveIconIntoBox(srcIcons, box.getIcons(), dragIcon, icon);
                                    isDroped = true;
                                }
                            }
                            break;
                    }
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
    private void insertIcons(List<IconBase> srcIcons, List<IconBase> dstIcons, IconBase srcIcon, IconBase dstIcon, IconWindow window, boolean animate)
    {
        int index = dstIcons.indexOf(dstIcon);
        if (index == -1) return;

        srcIcons.remove(srcIcon);
        dstIcons.add(index, srcIcon);

        // 再配置
        if (animate) {
            if (srcIcons != dstIcons) {
                // ドロップアイコンの座標系を変換
                dragIcon.setPos(srcIcon.pos.x + this.pos.x - window.pos.x,
                        srcIcon.pos.y + this.pos.y - window.pos.y);
                window.sortRects(animate);
            }
            sortRects(animate);
        }
    }

    /**
     * アイコンを移動する
     * アイコンを別のボックスタイプのアイコンにドロップした時に使用する
     * @param srcIcons ドラッグ元のIcons
     * @param dstIcons ドロップ先のIcons
     * @param srcIcon ドロップ元のIcon
     * @param dstIcon ドロップ先のIcon
     */
    private void moveIconIntoBox(List<IconBase> srcIcons, List<IconBase> dstIcons, IconBase srcIcon, IconBase dstIcon)
    {
        srcIcons.remove(srcIcon);
        dstIcons.add(srcIcon);

        if (dstIcon instanceof IconBox) {
            IconBox box = (IconBox)dstIcon;
            if (box.getSubWindow() != null) {
                box.getSubWindow().sortRects(false);
            }
        }

        sortRects(true);
    }
}
