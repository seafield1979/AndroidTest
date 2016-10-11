package com.example.shutaro.testbutterknife;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyFragment extends Fragment {

    @InjectView(R.id.textView)
    TextView mTextView;
    @InjectView(R.id.fragmentButton1)
    Button mButton;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @OnClick({R.id.fragmentButton1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragmentButton1: {
                mTextView.append("push!\n");
            }
            break;
        }
    }
}
