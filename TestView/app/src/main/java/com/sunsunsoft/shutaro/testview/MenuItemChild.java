package com.sunsunsoft.shutaro.testview;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * メニューバーの子要素
 * メニューバーのトップ要素をクリックすると表示される
 */

public class MenuItemChild extends MenuItem{

    public MenuItemChild(MenuItemId id, Bitmap icon) {
        super(id, icon);
    }

    public boolean checkClick(float clickX, float clickY) {
        if (pos.x <= clickX && clickX <= pos.x + ITEM_W &&
                pos.y <= clickY && clickY <= pos.y + ITEM_H)
        {
            Log.d("MenuItem", "clicked");
            // タッチされた時の処理
            if (mCallbacks != null) {
                mCallbacks.callback1(id);
            }
            return true;
        }
        return false;
    }
}
