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


public class MyFragment6 extends Fragment implements OnClickListener, OnTouchListener, TouchEventCallbacks{
    private final static String BACKGROUND_COLOR = "background_color";
    private MyView6 myView;

    public static MyFragment6 newInstance(@ColorRes int IdRes) {
        MyFragment6 frag = new MyFragment6();
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
        View view = inflater.inflate(R.layout.fragment_page6, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        myView = (MyView6)view.findViewById(R.id.myView6);
        myView.setCallbacks(this);
        myView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2000));

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myView.sortRects(true);
                myView.invalidate();
                break;
            case R.id.button2:
                ViewSettings.drawIconId = !ViewSettings.drawIconId;
                myView.invalidate();
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
