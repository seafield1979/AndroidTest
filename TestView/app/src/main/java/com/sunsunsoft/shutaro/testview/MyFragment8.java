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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

/**
 * Viewに表示しきれない領域をスクロールできるようにする
 * ScrollViewを使う方式
 */
public class MyFragment8 extends Fragment implements OnClickListener, OnTouchListener{
    private final static String BACKGROUND_COLOR = "background_color";
    private MyView8 myView;
    private Button button;

    public static MyFragment8 newInstance(@ColorRes int IdRes) {
        MyFragment8 frag = new MyFragment8();
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
        View view = inflater.inflate(R.layout.fragment_page8, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        myView = (MyView8)view.findViewById(R.id.myView8);
//        myView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2000));

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myView.updateViewSize(1000, 700);
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
