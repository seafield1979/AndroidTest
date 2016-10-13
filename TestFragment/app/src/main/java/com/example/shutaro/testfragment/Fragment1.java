package com.example.shutaro.testfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment{
    public static final String FRAMGMENT_NAME = Fragment1.class.getName();

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.textView)
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment21, container, false);
        ButterKnife.inject(this, view);

        textView.append("hogehogehoge\n");

        return view;
    }

@Override
public void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
}

@OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                textView.append("button\n");
                break;
        }
    }
}
