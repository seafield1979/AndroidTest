package com.example.shutaro.testfragment;


import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
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
    public static final String KEY_STR1 = "str1";
    public static final String KEY_INT1 = "int1";

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.textView)
    TextView textView;

    // このメソッドからFragmentを作成することを強制する
    @CheckResult
    public static MainFragment createInstance(String str1, int int1) {
        // Fragmentを作成して返すメソッド
        // createInstanceメソッドを使用することで、そのクラスを作成する際にどのような値が必要になるか制約を設けることができる
        MainFragment fragment = new MainFragment();

        // 引数をセット
        Bundle args = new Bundle();
        args.putString(KEY_STR1, str1);
        args.putInt(KEY_INT1, int1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment21, container, false);
        ButterKnife.inject(this, view);

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
            {

            }
                break;
        }
    }
}
