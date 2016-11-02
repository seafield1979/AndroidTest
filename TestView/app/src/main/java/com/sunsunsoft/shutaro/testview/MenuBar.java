package com.sunsunsoft.shutaro.testview;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;

/**
 * メニューバー
 * メニューに表示する項目を管理する
 */
public class MenuBar {

    private float x, y;
    private int width, height;
    List<MenuItem> items;

    public MenuBar() {
        items = new LinkedList<MenuItem>();
    }


    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void draw(Canvas canvas, Paint paint) {

    }
}
