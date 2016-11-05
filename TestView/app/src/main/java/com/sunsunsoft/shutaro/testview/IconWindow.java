package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * アイコンのリストを表示するWindow
 */

public class IconWindow implements AutoMovable{
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
    private static final int SCROLL_BAR_W = 100;


    // メンバ変数
    private boolean isShow = true;
    private PointF pos = new PointF();
    private Size size = new Size();
    private RectF rect = new RectF();
    private int bgColor;

    private int skipFrame = 3;  // n回に1回描画
    private int skipCount;

    // Window移動用
    protected boolean isMoving;
    protected int movingFrame;
    protected int movingFrameMax;
    protected PointF srcPos = new PointF();
    protected PointF dstPos = new PointF();

    // アイコン移動用
    private IconWindow[] windows;

    // スクロール用
    private Size contentSize = new Size();     // 領域全体のサイズ
    private PointF contentTop = new PointF();  // 画面に表示する領域の左上の座標
    MyScrollBar mScrollBar;

    // ドラッグ中のアイコン
    private IconBase dragIcon;

    // アニメーション用
    private viewState state = viewState.none;

    private LinkedList<IconBase> icons = new LinkedList<IconBase>();

    // Get/Set
    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setPos(float x, float y, boolean update) {
        pos.x = x;
        pos.y = y;
        if (update) {
            updateRect();
        }
    }
    public void setWindows(IconWindow[] windows) {
        this.windows = windows;
    }

    private void updateRect() {
        rect.left = pos.x;
        rect.right = pos.x + size.width;
        rect.top = pos.y;
        rect.bottom = pos.y + size.height;
    }
    private void setSize(int width, int height) {
        size.width = width;
        size.height = height;
    }

    public PointF getContentTop() {
        return contentTop;
    }

    public void setContentTop(PointF contentTop) {
        this.contentTop = contentTop;
    }

    public void setContentTop(float x, float y) {
        contentTop.x = x;
        contentTop.y = y;
    }


    // 座標系を変換する
    // 座標系は以下の３つある
    // 1.Screen座標系  画面上の左上原点
    // 2.Window座標系  ウィンドウ左上原点 + スクロールして表示されている左上が原点

    // Screen座標系 -> Window座標系
    public float toWinX(float screenX) {
        return screenX + contentTop.x - pos.x;
    }
    public float toWinY(float screenY) {
        return screenY + contentTop.y - pos.y;
    }

    // Windows座標系 -> Screen座標系
    public float toScreenX(float winX) {
        return winX - contentTop.x + pos.x;
    }
    public float toScreenY(float winY) {
        return winY - contentTop.y + pos.y;
    }

    // Window1の座標系から Window2の座標系に変換
    public float win1ToWin2X(float win1X, IconWindow win1, IconWindow win2) {
        return win1X + win1.pos.x - win1.contentTop.x - win2.pos.x + win2.contentTop.x;
    }
    public float win1ToWin2Y(float win1Y, IconWindow win1, IconWindow win2) {
        return win1Y + win1.pos.y - win1.contentTop.y - win2.pos.y + win2.contentTop.y;
    }

    // Window2座標系 -> Screen座標系に変換するための値
    // Window内のオブジェクトを描画する際にこの値を加算する
    public PointF getWin2ScreenPos() {
        return new PointF(pos.x - contentTop.x, pos.y - contentTop.y);
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
        setPos(x, y,false);
        setSize(width, height);
        this.bgColor = bgColor;

        updateRect();

        mScrollBar = new MyScrollBar(ScrollBarType.Right, ScrollBarInOut.In, this.pos, width, height, SCROLL_BAR_W, contentSize.height);
        mScrollBar.setBgColor(Color.rgb(128,128,128));

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

    public void setContentSize(int width, int height) {
        contentSize.width = width;
        contentSize.height = height;
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
    public void updateSize(int width, int height) {
        setSize(width, height);
        updateRect();

        // スクロールをクリア
        setContentTop(0, 0);

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
            if (!(window.rect.left <= vt.getX() && vt.getX() <= window.rect.right &&
                    window.rect.top <= vt.getY() && vt.getY() <= window.rect.bottom) )
            {
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
                    }
                } else {
                    // ドラッグ中のアイコンをリストの最後に移動
                    srcIcons.remove(dragIcon);
                    dstIcons.add(dragIcon);
                }

                // 再配置
                if (srcIcons != dstIcons) {
                    // 座標系変換(移動元Windowから移動先Window)
                    dragIcon.setPos(win1ToWin2X(dragIcon.pos.x, this, window), win1ToWin2Y(dragIcon.pos.y, this, window));

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
        mScrollBar.updateScroll(contentTop);

        return true;
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
        if (vt.touchX() < rect.left || rect.right < vt.touchX() ||
                vt.touchY() < rect.top || rect.bottom < vt.touchY())
        {
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

    /**
     * 自動移動開始
     * @param dstX  目的位置x
     * @param dstY  目的位置y
     * @param frame  移動にかかるフレーム数
     */
    public void startMove(float dstX, float dstY, int frame) {
        if (pos.x == dstX && pos.y == dstY) {
            return;
        }
        srcPos.x = pos.x;
        srcPos.y = pos.y;
        dstPos.x = dstX;
        dstPos.y = dstY;
        movingFrame = 0;
        movingFrameMax = frame;
        isMoving = true;
    }

    /**
     * 移動
     * 移動開始位置、終了位置、経過フレームから現在位置を計算する
     * @return 移動完了したらtrue
     */
    public boolean move() {
        if (!isMoving) return true;

        boolean ret = false;

        float ratio = (float)movingFrame / (float)movingFrameMax;
        pos.x = srcPos.x + ((dstPos.x - srcPos.x) * ratio);
        pos.y = srcPos.y + ((dstPos.y - srcPos.y) * ratio);

        movingFrame++;
        if (movingFrame >= movingFrameMax) {
            isMoving = false;
            pos.x = dstPos.x;
            pos.y = dstPos.y;

            ret = true;
        }
        updateRect();
        return ret;
    }
}
