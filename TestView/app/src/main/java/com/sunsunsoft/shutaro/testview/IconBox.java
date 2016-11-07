package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.LinkedList;

/**
 * 子要素を持つことができるIcon
 */

public class IconBox extends IconBase {

    public static final int DUMMY_ICON_NUM = 10;

    private IconManager iconManager;

    public IconBox(IconWindow parent, int x, int y, int width, int height) {
        super(parent, IconShape.BOX, x,y,width,height);

        color = Color.rgb(0,255,255);

        // ダミーで子要素を追加
        for (int i=0; i<DUMMY_ICON_NUM; i++) {
            iconManager.addIcon(IconShape.RECT, AddPos.Tail);
        }
    }

    public boolean draw(Canvas canvas, Paint paint)
    {
        return draw(canvas, paint, null, null);
    }


    public boolean draw(Canvas canvas, Paint paint, PointF toScreen, RectF clipRect) {
        if (toScreen == null) {
            toScreen = new PointF(0, 0);
        }
        float drawX = pos.x + toScreen.x;
        float drawY = pos.y + toScreen.y;
        RectF rect = new RectF(drawX, drawY, drawX + size.width, drawY + size.height);

        // クリッピング処理
        // 表示領域外なら描画しない
        if (clipRect != null) {
            if (rect.contains(clipRect)) {
                return false;
            }
        }

        // 内部を塗りつぶし
        paint.setStyle(Paint.Style.FILL);
        // 色
        if (isAnimating) {
            double v1 = ((double)animeFrame / (double)animeFrameMax) * 180;
            int alpha = (int)((1.0 -  Math.sin(v1 * RAD)) * 255);
            paint.setColor((alpha << 24) | (color & 0xffffff));
        } else {
            paint.setColor(color);
        }
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom,  paint);

        drawId(canvas, paint);
        return true;
    }

    @Override
    public void click() {
        super.click();
    }

    @Override
    public void longClick() {
        super.longClick();
    }

    @Override
    public void moving() {
        super.moving();

    }

    @Override
    public void drop() {
        super.drop();

    }
}
