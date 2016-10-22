package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Size;

import static com.sunsunsoft.shutaro.testview.ViewSettings.drawIconId;

/**
 * ViewのonDrawで描画するアイコンの情報
 */
abstract public class MyIcon {
    private static int count;

    public int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected IconShape shape;

    protected int color;

    public MyIcon(IconShape shape, int x, int y, int width, int height) {
        this(shape, x,y,width,height, Color.rgb(0,0,0));
    }

    public MyIcon(IconShape shape, int x, int y, int width, int height, int color) {
        this.id = count;
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.rgb(0,0,0);
        count++;
    }

    abstract public void draw(Canvas canvas, Paint paint);

    // 座標、サイズのGet/Set
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getRight() {
        return x + width;
    }
    public int getBottom() {
        return y + height;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point getPos() {
        return new Point(x,y);
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int w) {
        width = w;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int h) {
        height = h;
    }

    public Size getSize() {
        return new Size(width, height);
    }
    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    // 移動
    public void move(int moveX, int moveY) {
        x += moveX;
        y += moveY;
    }

    // 色
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    // for debug
    protected void drawId(Canvas canvas, Paint paint) {
        // idを表示
        if (drawIconId) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            canvas.drawText("" + id, x+10, y+height-30, paint);
        }
    }
}
