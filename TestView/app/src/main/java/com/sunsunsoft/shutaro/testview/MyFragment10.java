package com.sunsunsoft.shutaro.testview;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * SubViewのテスト
 * 今までViewがアイコンのWindowを管理していたが、２つに増やすためにIconWindowクラスを作成する
 */
public class MyFragment10 extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private final static String BACKGROUND_COLOR = "background_color";
    private MyView10 myView;
    private static final int[] buttonIds = {
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5
    };

    public static MyFragment10 newInstance(@ColorRes int IdRes) {
        MyFragment10 frag = new MyFragment10();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, IdRes);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page10, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        myView = (MyView10)view.findViewById(R.id.myView10);

        for (int id : buttonIds) {
            ((Button) view.findViewById(id)).setOnClickListener(this);
        }

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myView.updateShow(myView.getWidth(),myView.getHeight() - 200);
                break;
            case R.id.button2:
                myView.updateShow2(myView.getWidth(),myView.getHeight() - 200);
                break;
            case R.id.button3:
                myView.moveTest1();
                break;
            case R.id.button4:
                myView.moveTest2();
                break;
            case R.id.button5:
                myView.showText();
                break;
        }
    }

    public boolean onTouch(View v, MotionEvent e) {
        return true;
    }

    //TouchEventCallbacks
    // 子Viewをタッチしている最中はスクロールしないようにする
    public void touchCallback(int action) {
    }
}