package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.util.LinkedList;

/**
 * メニューバーのトップ項目
 */

public class MenuItemTop extends MenuItem{

    private static final int CHILD_MARGIN_V = 50;

    // Member variables
    private LinkedList<MenuItemChild> childItems;
    private boolean isOpened;


    // Constructor
    public MenuItemTop(MenuItemId id, Bitmap icon) {
        super(id, icon);
    }

    // Get/Set
    public LinkedList<MenuItemChild> getChildItems() {
        return childItems;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }


    /**
     * 子要素を追加する
     * @param item
     */
    public void addItem(MenuItemChild item) {
        if (childItems == null) {
            childItems = new LinkedList<MenuItemChild>();
        }

        // 座標を設定する
        item.setPos( pos.x, pos.y - ((childItems.size() + 1) * (MenuItem.ITEM_H + CHILD_MARGIN_V)));

        childItems.add(item);
    }

    /**
     * 描画処理
     * @param canvas
     * @param paint
     * @param parentPos
     */
    public void draw(Canvas canvas, Paint paint, PointF parentPos) {
        super.draw(canvas, paint, parentPos);

        // 子要素をまとめて描画
        PointF _pos = new PointF();
        PointF drawPos = new PointF(pos.x - parentPos.x, pos.y - parentPos.y);
        if (isOpened && childItems != null) {
            for (int i=0; i<childItems.size(); i++) {
                MenuItem child = childItems.get(i);
                child.draw(canvas, paint, parentPos);
            }
        }
    }

    /**
     * クリック処理
     * @param clickX
     * @param clickY
     * @return
     */
    public boolean checkClick(float clickX, float clickY) {
        if (pos.x <= clickX && clickX <= pos.x + ITEM_W &&
                pos.y <= clickY && clickY <= pos.y + ITEM_H)
        {
            Log.d("MenuItem", "clicked");
            // 子要素を持っていたら Open/Close
            if (childItems != null) {
                if (isOpened) {
                    isOpened = false;
                } else {
                    isOpened = true;
                }
                Log.d("MenuItem", "isOpened " + isOpened);
            }

            // タッチされた時の処理
            if (mCallbacks != null) {
                mCallbacks.callback1(id);
            }

            return true;
        }

        // 子要素
        if (isOpened()) {
            if (childItems != null) {
                for (MenuItemChild child : childItems) {
                    if (child.checkClick(clickX, clickY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
