package com.sunsunsoft.shutaro.testview;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment3 extends Fragment implements OnClickListener, OnTouchListener {
    private final static String BACKGROUND_COLOR = "background_color";
    private Button updateButton;
    private MyView3 myView3;

    public static MyFragment3 newInstance(@ColorRes int IdRes) {
        MyFragment3 frag = new MyFragment3();
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
        View view = inflater.inflate(R.layout.fragment_page3, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page3_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        updateButton = (Button)view.findViewById(R.id.button);
        updateButton.setOnClickListener(this);

        myView3 = (MyView3)view.findViewById(R.id.myView3);

        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                myView3.invalidate();
                break;
        }
    }

    public boolean onTouch(View v, MotionEvent e) {
        return false;
    }
}
