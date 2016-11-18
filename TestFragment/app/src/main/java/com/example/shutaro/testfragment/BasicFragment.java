package com.example.shutaro.testfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * シンプルなFragment
 *
 *
 */
public class BasicFragment extends Fragment implements OnClickListener, OnKeyListener{
    public static final String FRAMGMENT_NAME = MainFragment.class.getName();
    private static final String TAG = FRAMGMENT_NAME;
    private final static String KEY_NAME = "key_name";
    private final static String KEY_BACKGROUND = "key_background_color";

    // 引数
    private String mName;
    private int mBackgroundColor;

    /**
     * このメソッドからFragmentを作成することを強制する
     */
    @CheckResult
    public static BasicFragment createInstance(String name, int color) {
        // Fragmentを作成して返すメソッド
        BasicFragment fragment = new BasicFragment();

        // 生成したFragmentのインスタンスに引数をセットする
        Bundle args = new Bundle();
        args.putString(KEY_NAME, name);
        args.putInt(KEY_BACKGROUND, color);
        fragment.setArguments(args);


        return fragment;
    }


    /**
     * Fragmentが生成された。Viewはまだ生成されていない
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 引数を受け取る
        Bundle args = getArguments();
        if (args != null) {
            mName = args.getString(KEY_NAME);
            mBackgroundColor = args.getInt(KEY_BACKGROUND, Color.TRANSPARENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic, container, false);

        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);

        return view;
    }

    /**
     * Viewが生成された
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 子Viewを初期化
        ((TextView)view.findViewById(R.id.textView)).setText(mName);
        ((Button)view.findViewById(R.id.button)).setOnClickListener(this);


        view.findViewById(R.id.bg_layout).setBackgroundColor(mBackgroundColor);

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                Log.d(TAG,"button clicked");
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // ここにバックキーで動かすコードを入れる
            return true;
        }
        return false;
    }

}
