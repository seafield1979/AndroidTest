package com.example.shutaro.testfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shutaro on 2016/09/28.
 */
public class MainFragment extends Fragment {
    public static final String FRAMGMENT_NAME = MainFragment.class.getName();

    @InjectView(R.id.textView)
    TextView textView;
    StringBuffer strBuff = new StringBuffer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String url = bundle.getString("URL");

        View view = inflater.inflate(R.layout.fragment1, container, false);
        ButterKnife.inject(this, view);

        textView.setText(strBuff.toString());
        strBuff.setLength(0);

        Log.i("Life", "onCreateView");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Life", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Life", "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Life", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Life", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Life", "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i("Life", "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.i("Life", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Life", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Life", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Life", "onDetach");
    }
}
