package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

// メニューバーのトップ項目
enum TopMenu {
    Add,            // 追加
    Sort,           // 並び替え
    ListType,        // リストの表示方法
    TopMenuMax
}

/**
 * メニューバー
 * メニューに表示する項目を管理する
 */
public class MenuBar {
    private static final int MENU_BAR_H = 150;
    private static final int MARGIN_L = 30;
    private static final int MARGIN_LR = 50;
    private static final int MARGIN_TOP = 30;
    public static final int TOP_MENU_MAX = TopMenu.TopMenuMax.ordinal();

    private PointF pos = new PointF();
    private int width, height;
    MenuItemTop[] topItems = new MenuItemTop[TOP_MENU_MAX];

    public MenuBar(int viewW, int viewH) {
        pos.x = 0;
        pos.y = viewH - MENU_BAR_H - 100;
        width = viewW;
        height = MENU_BAR_H;
    }

    /**
     * 項目を追加する
     * @param index   項目を追加する位置
     * @param item    追加する項目
     */
    public void addItem(TopMenu index, MenuItemTop item) {
        int pos = index.ordinal();
        if (pos >= TOP_MENU_MAX) return;

        // 項目の座標を設定
        // メニューバーの左上原点の相対座標、スクリーン座標は計算で求める
        item.setPos(MARGIN_L + pos * (MenuItem.ITEM_W + MARGIN_LR), MARGIN_TOP);

        topItems[pos] = item;
    }

    /**
     * 子要素を追加する
     * @param index
     * @param item
     */
    public void addChildItem(TopMenu index, MenuItemChild item) {
        MenuItemTop topItem = topItems[index.ordinal()];
        if (topItem == null) return;

        topItem.addItem(item);
    }

    /**
     * 表示する
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        // bg
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(0xff000000);

        canvas.drawRect(pos.x,
                pos.y,
                pos.x + width,
                pos.y + height,
                paint);


        for (MenuItemTop item : topItems) {
            if (item != null) {
                item.draw(canvas, paint, pos);
            }
        }
    }

    /**
     * メニューのアクション
     * メニューアイテムを含めて何かしらの処理を行う
     *
     * @return true:処理中 / false:完了
     */
    public boolean doAction() {
        MyLog.print("MenuBar", "doAction");

        boolean allFinished = true;
        for (MenuItemTop item : topItems) {
            if (item.moveChilds()) {
                allFinished = false;
            }
        }

        return !allFinished;
    }

    /**
     * クリックをチェックする
     * メニューバー以下の項目(メニューの子要素も含めて全て)のクリック判定
     */
    public boolean checkClick(float clickX, float clickY) {
        // 渡されるクリック座標をメニューバーの座標系に変換
        clickX -= pos.x;
        clickY -= pos.y;
        boolean done = false;
        for (int i=0; i<topItems.length; i++) {
            MenuItemTop item = topItems[i];
            if (item == null) continue;

            if (item.checkClick(clickX, clickY)) {
                done = true;
                if (item.isOpened()) {
                    // 他に開かれたメニューを閉じる
                    closeAllMenu(i);
                }
                break;
            }
            if (done) break;
        }

        // メニューバーの領域をクリックしていたら、メニュー以外がクリックされるのを防ぐためにtrueを返す
        if (!done) {
            if (0 <= clickX && clickX <= width &&
                    0 <= clickY && clickY <= height)
            {
                return true;
            }
        }

        return done;
    }

    /**
     * メニューを閉じる
     * @param excludedIndex
     */
    private void closeAllMenu(int excludedIndex) {
        for (int i = 0; i < topItems.length; i++) {
            if (i == excludedIndex) continue;
            MenuItemTop item = topItems[i];
            item.closeMenu();
        }
    }

}
