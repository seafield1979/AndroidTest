package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import static com.sunsunsoft.shutaro.testview.ViewSettings.drawIconId;

/**
 * ViewのonDrawで描画するアイコンの情報
 */
abstract public class IconBase implements AutoMovable {

    private static final String TAG = "MyIcon";
    private static int count;

    public int id;
    protected PointF pos = new PointF();
    protected Size size = new Size();

    // 移動用
    protected boolean isMoving;
    protected int movingFrame;
    protected int movingFrameMax;
    protected float srcX, srcY;
    protected float dstX, dstY;

    protected IconShape shape;

    protected int color;

    public IconBase(IconShape shape, float x, float y, int width, int height) {
        this(shape, x,y,width,height, Color.rgb(0,0,0));
    }

    public IconBase(IconShape shape, float x, float y, int width, int height, int color) {
        this.id = count;
        this.shape = shape;
        this.setPos(x, y);
        this.setSize(width, height);
        this.color = Color.rgb(0,0,0);
        count++;
    }

    abstract public boolean draw(Canvas canvas, Paint paint);
    abstract public boolean draw(Canvas canvas, Paint paint, PointF top, RectF clipRect);

    /**
     * クリッピング
     * オブジェクトが親Windowの範囲内にあるかどうかを判定する
     * @param iconRect
     * @param clipRect
     * @return true: 範囲外(描画しない) / false:範囲内
     */
    public boolean isClip(RectF iconRect, RectF clipRect) {
        if (iconRect.right < clipRect.left ||
                iconRect.left > clipRect.right ||
                iconRect.bottom < clipRect.top ||
                iconRect.top > clipRect.bottom )
        {
            return true;
        }
        return false;
    }

    public IconShape getShape() { return shape; }


    // 座標、サイズのGet/Set
    public float getX() {
        return pos.x;
    }
    public void setX(float x) {
        pos.x = x;
    }

    public float getY() {
        return pos.y;
    }
    public void setY(float y) {
        pos.y = y;
    }

    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
    }

    public float getRight() {
        return pos.x + size.width;
    }
    public float getBottom() {
        return pos.y + size.height;
    }

    public int getWidth() {
        return size.width;
    }
    public void setWidth(int w) {
        size.width = w;
    }

    public int getHeight() {
        return size.height;
    }
    public void setHeight(int h) {
        size.height = h;
    }

    public void setSize(int width, int height) {
        size.width = width;
        size.height = height;
    }

    // 移動
    public void move(float moveX, float moveY) {
        pos.x += moveX;
        pos.y += moveY;
    }

    // 色
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
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
        srcX = pos.x;
        srcY = pos.y;
        this.dstX = dstX;
        this.dstY = dstY;
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

        float ratio = (float)movingFrame / (float)movingFrameMax;
        pos.x = srcX + ((dstX - srcX) * ratio);
        pos.y = srcY + ((dstY - srcY) * ratio);


        movingFrame++;
        if (movingFrame >= movingFrameMax) {
            isMoving = false;
            pos.x = dstX;
            pos.y = dstY;
            return true;
        }
        return false;
    }

    public void click() {
        Log.v(TAG, "click");
    }
    public void longClick() {
        Log.v(TAG, "long click");
    }
    public void moving() {
        Log.v(TAG, "moving");
    }
    public void drop() {
        Log.v(TAG, "drop");
    }

    /**
     * アイコンのタッチ処理
     * @param tx
     * @param ty
     * @return
     */
    public boolean checkTouch(float tx, float ty) {
        if (pos.x <= tx && tx <= getRight() &&
                pos.y <= ty && ty <= getBottom() )
        {
            return true;
        }
        return false;
    }

    /**
     * クリックのチェックとクリック処理。このメソッドはすでにクリック判定された後の座標が渡される
     * @param clickX
     * @param clickY
     * @return
     */
    public boolean checkClick(float clickX, float clickY) {
        if (pos.x <= clickX && clickX <= getRight() &&
                pos.y <= clickY && clickY <= getBottom() )
        {
            click();
            return true;
        }
        return false;
    }

    /**
     * ドロップをチェックする
     */
    public boolean checkDrop(float dropX, float dropY) {
        if (pos.x <= dropX && dropX <= getRight() &&
                pos.y <= dropY && dropY <= getBottom() )
        {
            return true;
        }
        return false;
    }

    /**
     * アイコンにIDを表示する
     * @param canvas
     * @param paint
     */
    protected void drawId(Canvas canvas, Paint paint) {
        // idを表示
        if (drawIconId) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("" + id, pos.x+10, pos.y + size.height - 30, paint);
        }
    }
}
