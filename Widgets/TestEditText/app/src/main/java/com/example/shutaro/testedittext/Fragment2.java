package com.example.shutaro.testedittext;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.R.attr.layout_marginTop;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements OnClickListener {
    public static final String FRAMGMENT_NAME = Fragment2.class.getName();

    private static int[] buttonIds = {
            R.id.button,
            R.id.button2
    };

    private ViewGroup layoutTop;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);

        layoutTop = (ViewGroup)view.findViewById(R.id.top_layout);

        for (int id : buttonIds) {
            ((Button)view.findViewById(id)).setOnClickListener(this);
        }

        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                Button button = new Button(getContext());
                button.setText("hogehoge");
                layoutTop.addView(button);
            }
                break;
            case R.id.button2:
            {
                EditText edit = new EditText(getContext());
                edit.setText("hogehoge");

                RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                layout.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.top_layout);
                layout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, R.id.top_layout);
                layout.setMargins(0,200,0,0);
                edit.setLayoutParams(layout);

                layoutTop.addView(edit, layout);
            }
                break;
        }
    }

}
