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
    private Button button;

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

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myView.updateWindowPos(100,100);
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