package com.sunsunsoft.shutaro.testevent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnLongClickListener;

/**
 * クリックイベントのテスト
 */

public class MyFragment extends Fragment implements View.OnClickListener, OnLongClickListener{
    private final static String BACKGROUND_COLOR = "background_color";

    TextView mTextView;

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

        mTextView = (TextView)view.findViewById(R.id.textView);

        // イベントリスナを登録
        // 親クラスのイベントリスナを使用
        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(this);

        // 無名リスナを使用
        Button button2 = (Button)view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("button2 clicked");
            }
        });

        Button button3 = (Button)view.findViewById(R.id.button3);
        button3.setOnLongClickListener(this);

        return view;
    }

    /**
     * クリック
     * @param v
     */
    public void onClick(View v) {
        String id = getActivity().getResources().getResourceEntryName(v.getId());

        switch(v.getId()) {
            case R.id.button:
                break;
        }
        mTextView.setText(id + " clicked");
    }

    /**
     * 長押し
     * @param v
     * @return
     */
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                break;
        }
        String id = getActivity().getResources().getResourceEntryName(v.getId());

        switch(v.getId()) {
            case R.id.button:
                break;
        }
        mTextView.setText(id + " long clicked");
        return true;
    }
}
