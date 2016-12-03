package com.sunsunsoft.shutaro.testpagerapp;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;

public class MyFragment extends Fragment {
    private final static String BACKGROUND_COLOR = "background_color";

    public static MyFragment newInstance(@ColorRes int IdRes) {
        MyFragment frag = new MyFragment();
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
        View view = inflater.inflate(R.layout.fragment_page1, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page1_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        return view;
    }


    /**
     * タッチイベント
     * @param v
     * @param e
     * @return
     */
//    public boolean onTouch(View v, MotionEvent e) {
//
//        switch(e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//
//            break;
//        }
//
//        return true;
//    }

}
