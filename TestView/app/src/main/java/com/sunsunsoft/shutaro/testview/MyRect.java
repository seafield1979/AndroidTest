package com.sunsunsoft.shutaro.testview;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * 自前のRectクラス
 */

public class MyRect {
    /**
     * 重なっているかチェック
     * @param rect1
     * @param rect2
     * @return true:一部分でも重なっている / false:全く重なっていない
     */
    public static boolean isOverlapping(RectF rect1, RectF rect2) {
        if (rect1.right < rect2.left || rect1.left > rect2.right ||
                rect1.bottom < rect2.top || rect1.top > rect2.bottom )
        {
            return false;
        }
        return true;
    }
}
