package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

/**
 * アイコンを内包するRectを管理するクラス
 * 配下のアイコンが全て収まる大きなRectを求めておき、
 * まずはこの大きなRectと判定を行い、重なっていた場合にのみ個々のアイコンと判定する
 */
public class IconsBlockManager {
    public static final String TAG = "IconsBlockManager";
    LinkedList<IconsBlock> blockList = new LinkedList<>();
    List<IconBase> icons;

    /**
     * インスタンスを取得
     * @param icons
     * @return
     */
    public static IconsBlockManager createInstance(List<IconBase> icons) {
        IconsBlockManager instance = new IconsBlockManager();
        instance.icons = icons;
        return instance;
    }

    /**
     * アイコンリストを設定する
     * アイコンリストはアニメーションが終わって座標が確定した時点で行う
     */
    public void setIcons(List<IconBase> icons) {
        this.icons = icons;
        update();
    }

    /**
     * IconsBlockのリストを作成する
     */
    public void update() {
        if (icons == null) return;
        if (blockList != null) {
            blockList.clear();
        }

        IconsBlock block = null;
        for (IconBase icon : icons) {
            if (block == null) {
                block = new IconsBlock();
            }

            if (block.add(icon)) {
                // ブロックがいっぱいになったのでRectを更新してから次のブロックを作成する
                block.updateRect();
                blockList.add(block);
                // 次のアイコンがあるとも限らないのでここでからにしておく
                block = null;
            }
        }

        if (block != null) {
            block.updateRect();
            blockList.add(block);
        }

        showLog();
    }

    /**
     * 指定座標に重なるアイコンを取得する
     * @param pos
     * @return
     */
    public IconBase getOverlapedIcon(Point pos, IconBase exceptIcon) {
        MyLog.startCount(TAG);
        for (IconsBlock block : blockList) {
            IconBase icon = block.getOverlapedIcon(pos, exceptIcon);
            if (icon != null) {
                MyLog.endCount(TAG);
                return icon;
            }
        }
        MyLog.endCount(TAG);
        return null;
    }

    private void showLog() {
        // debug
        for (IconsBlock block : blockList) {
            Rect _rect = block.getRect();
            MyLog.print(TAG, "count:" + block.getIcons().size() + " L:" + _rect.left + " R:" + _rect
                    .right +
                    " " +
                    "U:" +
                    _rect.top + " D:" + _rect.bottom);
        }
    }

    /**
     * IconsBlockの矩形を描画 for Debug
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint, PointF toScreenPos) {
        for (IconsBlock block : blockList) {
            block.draw(canvas, paint, toScreenPos);
        }
    }
}

/**
 * １ブロックのクラス
 */
class IconsBlock {
    private static final int BLOCK_ICON_MAX = 8;

    private LinkedList<IconBase> icons = new LinkedList<>();
    private Rect rect = new Rect();
    private int color = MyColor.BLACK;

    // Get/Set
    public Rect getRect() {
        return rect;
    }

    public LinkedList<IconBase> getIcons() {
        return icons;
    }

    /**
     * アイコンをブロックに追加する
     * @param icon
     * @return true:リストがいっぱい
     */
    public boolean add(IconBase icon) {
        icons.add(icon);
        if (icons.size() >= BLOCK_ICON_MAX) {
            return true;
        }
        return false;
    }

    /**
     * ブロックの矩形を更新
     */
    public void updateRect() {
        rect.left = 1000000;
        rect.top = 1000000;
        for (IconBase icon : icons) {
            if (icon.pos.x < rect.left) {
                rect.left = (int)icon.pos.x;
            }
            if (icon.getRight() > rect.right) {
                rect.right = (int)icon.getRight();
            }
            if (icon.pos.y < rect.top) {
                rect.top = (int)icon.pos.y;
            }
            if (icon.getBottom() > rect.bottom) {
                rect.bottom = (int)icon.getBottom();
            }
        }
    }

    /**
     * ブロックとの重なり判定
     * ブロックと重なっていたら個々のアイコンとも判定を行う
     * @param pos
     * @param exceptIcon
     * @return null:重なるアイコンなし
     */
    public IconBase getOverlapedIcon(Point pos, IconBase exceptIcon) {
//        MyLog.count(IconsBlockManager.TAG);
        if (rect.contains(pos.x, pos.y)) {
            for (IconBase icon : icons) {
                if (icon == exceptIcon) continue;
                MyLog.count(IconsBlockManager.TAG);
                if (icon.getRect().contains(pos.x, pos.y)) {
                    return icon;
                }
            }
        }
        return null;
    }

    /**
     * 矩形を描画(for Debug)
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint, PointF toScreenPos) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(color);
        canvas.drawRect(rect.left + toScreenPos.x, rect.top + toScreenPos.y,
                rect.right + toScreenPos.x, rect.bottom + toScreenPos.y,  paint);
    }
}
