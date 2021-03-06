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


public class MyFragment4 extends Fragment implements OnClickListener, OnTouchListener, TouchEventCallbacks{
    private final static String BACKGROUND_COLOR = "background_color";
    private Button updateButton;
    private Button showIdButton;
    private MyView4 myView4;

    public static MyFragment4 newInstance(@ColorRes int IdRes) {
        MyFragment4 frag = new MyFragment4();
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
        View view = inflater.inflate(R.layout.fragment_page4, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        updateButton = (Button)view.findViewById(R.id.button);
        updateButton.setOnClickListener(this);

        showIdButton = (Button)view.findViewById(R.id.button2);
        showIdButton.setOnClickListener(this);

        myView4 = (MyView4)view.findViewById(R.id.myView4);
        myView4.setCallbacks(this);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                myView4.sortRects(true);
                myView4.invalidate();
                break;
            case R.id.button2:
                ViewSettings.drawIconId = !ViewSettings.drawIconId;
                myView4.invalidate();
                break;
        }
    }

    public boolean onTouch(View v, MotionEvent e) {
        return true;
    }

    //TouchEventCallbacks
    // 子Viewをタッチしている最中はスクロールしないようにする
    public void touchCallback(int action) {
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//            {
//                HoldableViewPager viewPager = (HoldableViewPager)getActivity().findViewById(R.id.main_viewpager);
//                viewPager.setSwipeHold(true);
//            }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            {
//                HoldableViewPager viewPager = (HoldableViewPager)getActivity().findViewById(R.id.main_viewpager);
//                viewPager.setSwipeHold(false);
//            }
//                break;
//            default:
//        }
    }
}
