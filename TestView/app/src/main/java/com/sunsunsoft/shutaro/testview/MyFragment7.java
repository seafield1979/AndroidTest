package com.sunsunsoft.shutaro.testview;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;

import static android.content.ContentValues.TAG;


/**
 * ScrollViewのスクロールを制御するサンプル
 */
public class MyFragment7 extends Fragment implements OnTouchListener{
    private final static String BACKGROUND_COLOR = "background_color";
    private MyScrollView myView;

    public static MyFragment7 newInstance(@ColorRes int IdRes) {
        MyFragment7 frag = new MyFragment7();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page7, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        myView = (MyScrollView) view.findViewById(R.id.myView);
        myView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2000));
        myView.setOnTouchListener(this);

        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e){
        String action = "";

        boolean ret = false;
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                ret = true;   // MOVEイベントを発生させる場合はtrueを返す
                if (e.getX() < v.getWidth() / 2) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (e.getX() < v.getWidth() / 2) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                break;
            default:
                action = "" + e.getAction();
        }
        Log.d("myview7", action);

        return ret;
    }
}
