package com.sunsunsoft.shutaro.testview;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;


public class MyFragment2 extends Fragment implements OnClickListener{
    private final static String BACKGROUND_COLOR = "background_color";

    private MyView2 myView2;
    private Button updateButton;

    public static MyFragment2 newInstance(@ColorRes int IdRes) {
        MyFragment2 frag = new MyFragment2();
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
        View view = inflater.inflate(R.layout.fragment_page2, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        updateButton = (Button)view.findViewById(R.id.button);
        updateButton.setOnClickListener(this);
        myView2 = (MyView2)view.findViewById(R.id.myView2);

        return view;
    }

    public void onClick(View v) {
         switch(v.getId()) {
             case R.id.button:
                 myView2.invalidate();
                 break;
         }
    }
}
