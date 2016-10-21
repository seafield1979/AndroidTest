package com.sunsunsoft.shutaro.testview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shutaro on 2016/10/21.
 */
public class HoldableViewPager extends android.support.v4.view.ViewPager {
    boolean isSwipeHold_ = false;   // スワイプによるページ切り替えを抑制する

    /*
     * スワイプによるページ切り替え有効/無効設定
     */
    public void setSwipeHold(boolean enable) {
        isSwipeHold_ = enable;
    }

    public HoldableViewPager(Context context) {
        super(context);
    }

    public HoldableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ( isSwipeHold_ ) return false;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)  {
        if ( isSwipeHold_ ) return false;
        return super.onInterceptTouchEvent(event);
    }
}