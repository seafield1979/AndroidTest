package com.example.shutaro.testfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shutaro on 2016/09/28.
 */
public class MainFragment extends Fragment {
    public static final String FRAMGMENT_NAME = MainFragment.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String url = bundle.getString("URL");

        View view = inflater.inflate(R.layout.fragment1, container, false);

        TextView textView = (TextView)view.findViewById(R.id.textView3);
        textView.setText(url);

        return view;
    }
}
