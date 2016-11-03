package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

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
abstract public class MenuItem{
    public static final int ITEM_W = 120;
    public static final int ITEM_H = 120;

    protected PointF pos = new PointF();

    protected MenuItemId id;

    // アイコン用画像
    protected Bitmap icon;

    protected MenuItemCallbacks mCallbacks;

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
    }

    /**
     * クリックをチェック
     * @param clickX
     * @param clickY
     */
    abstract public boolean checkClick(float clickX, float clickY);
}
