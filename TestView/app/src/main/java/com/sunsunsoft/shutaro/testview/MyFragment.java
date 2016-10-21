package com.sunsunsoft.shutaro.testview;

import android.graphics.Color;
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

public class MyFragment extends Fragment implements OnTouchListener, TouchEventCallbacks{
    private final static String BACKGROUND_COLOR = "background_color";

    private LinearLayout mContainer;
    private MyView myView;

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
        mContainer = (LinearLayout) view.findViewById(R.id.fragment_page1_linearlayout);
        mContainer.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        init();

        return view;
    }

    private void init() {
        // ただのViewを作成する
        // サイズの設定は setLayoutParams()メソッドで行う。
//        View view = new View(getActivity());
        View view = new View(getContext());
        view.setBackgroundColor(Color.rgb(255,255,0));
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100));
        view.setOnTouchListener(this);
        mContainer.addView(view);

        View view2 = new View(getActivity());
        view2.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        view2.setBackgroundColor(Color.rgb(255,0,255));
        view2.setOnTouchListener(this);
        mContainer.addView(view2);

        // 自作のView
        myView = new MyView(getContext());
        myView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        myView.setCallbacks(this);
        mContainer.addView(myView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e){
        String action = "";

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                break;
            default:
                action = "" + e.getAction();
        }

        int id = v.getId();
        String idString = "";
        if (id != -1) {
            idString = getContext().getResources().getResourceEntryName(id);
        }

        Log.v(TAG, "action:" + action + " id:" + idString + " x:" + e.getX() + " y:" + e.getY());

        return true;
    }

    //TouchEventCallbacks
    public void touchCallback(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            {
                HoldableViewPager viewPager = (HoldableViewPager)getActivity().findViewById(R.id.main_viewpager);
                viewPager.setSwipeHold(true);
            }
            break;
            case MotionEvent.ACTION_UP:
            {
                HoldableViewPager viewPager = (HoldableViewPager)getActivity().findViewById(R.id.main_viewpager);
                viewPager.setSwipeHold(false);
            }
            break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
    }
}
