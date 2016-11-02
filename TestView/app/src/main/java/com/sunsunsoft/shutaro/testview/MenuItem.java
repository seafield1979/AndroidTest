package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.util.LinkedList;


// メニューをタッチした時に返されるID
enum MenuItemId {
    AddTop,
    AddCard,
    AddBook,
    AddBox,
    SortTop,
    Sort1,
    Sort2,
    Sort3,
    ListTypeTop,
    ListType1,
    ListType2,
    ListType3
};

/**
 * メニューに表示する項目
 * アイコンを表示してタップされたらIDを返すぐらいの機能しか持たない
 */
public class MenuItem {
    public static final int ITEM_W = 120;
    public static final int ITEM_H = 120;
    private static final int CHILD_MARGIN_V = 50;

    private PointF pos = new PointF();

    // タップされた時に返すID
    private MenuItemId id;

    // アイコン用画像
    private Bitmap icon;

    private MenuItemCallbacks mCallbacks;

    private LinkedList<MenuItem> childItems;

    private boolean isOpened;

    public void setCallbacks(MenuItemCallbacks callbacks){
        mCallbacks = callbacks;
    }

    // Get/Set
    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
    }
    public PointF getPos() {
        return pos;
    }

    public MenuItem(MenuItemId id, Bitmap icon) {
        this.id = id;
        this.icon = icon;
    }

    public LinkedList<MenuItem> getChildItems() {
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
    public void addItem(MenuItem item) {
        if (childItems == null) {
            childItems = new LinkedList<MenuItem>();
        }
        childItems.add(item);
    }

    public void draw(Canvas canvas, Paint paint, PointF parentPos) {
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(0xffff0000);

        PointF drawPos = new PointF();
        drawPos.x = pos.x + parentPos.x;
        drawPos.y = pos.y + parentPos.y;

        canvas.drawRect(drawPos.x,
                drawPos.y,
                drawPos.x + ITEM_W,
                drawPos.y + ITEM_H,
                paint);

        if (icon != null) {
            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(icon, new Rect(0,0,icon.getWidth(), icon.getHeight()),
                    new Rect((int)drawPos.x, (int)drawPos.y, (int)drawPos.x + ITEM_W,(int)drawPos.y + ITEM_H),
                    paint);
        }

        // 子要素をまとめて描画
        PointF _pos = new PointF();
        if (isOpened && childItems != null) {
            for (int i=0; i<childItems.size(); i++) {
                MenuItem child = childItems.get(i);

                _pos.x = drawPos.x;
                _pos.y = drawPos.y - (i+1) * (ITEM_H + CHILD_MARGIN_V);
                child.drawAtPos(canvas, paint, _pos);
            }
        }
    }

    /**
     * 指定の座標に描画
     * 子メニューを描画するのに使用する
     */
    private void drawAtPos(Canvas canvas, Paint paint, PointF drawPos) {
        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        paint.setColor(0xffff0000);

        canvas.drawRect(drawPos.x,
                drawPos.y,
                drawPos.x + ITEM_W,
                drawPos.y + ITEM_H,
                paint);

        if (icon != null) {
            // 領域の幅に合わせて伸縮
            canvas.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()),
                    new Rect((int) drawPos.x, (int) drawPos.y, (int)drawPos.x + ITEM_W, (int) drawPos.y + ITEM_H),
                    paint);
        }
    }

    /**
     * クリックをチェック
     * @param clickX
     * @param clickY
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
        return false;
    }
}
