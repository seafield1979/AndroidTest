package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.LinkedList;

// アイコンの挿入位置
enum AddPos {
    Top,
    Tail
}

/**
 * IconWindowに表示するアイコンを管理するクラス
 */
public class IconManager {

    private View mParentView;
    private IconWindow mParentWindow;
    private LinkedList<IconBase> icons;

    // Get/Set
    public LinkedList<IconBase> getIcons() {
        return icons;
    }

    public void setIcons(LinkedList<IconBase> icons) {
        this.icons = icons;
    }

    public static IconManager createInstance(View parentView, IconWindow parentWindow) {
        IconManager instance = new IconManager();
        instance.mParentView = parentView;
        instance.mParentWindow = parentWindow;
        instance.icons = new LinkedList<>();
        return instance;
    }

    /**
     * 指定タイプのアイコンを追加
     * @param type
     * @param addPos
     * @return
     */
    public IconBase addIcon(IconShape type, AddPos addPos) {

        IconBase icon = null;
        switch (type) {
            case RECT:
                icon = new IconRect(mParentWindow);
                break;
            case CIRCLE:
                icon = new IconCircle(mParentWindow);
                break;
            case IMAGE: {
                Bitmap bmp = BitmapFactory.decodeResource(mParentView.getResources(), R.drawable.hogeman);
                icon = new IconBmp(mParentWindow, bmp);
            }
        }

        if (addPos == AddPos.Top) {
            icons.push(icon);
        } else {
            icons.add(icon);
        }

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

}
