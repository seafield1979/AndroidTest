package com.example.shutaro.testfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * 2つのFragmentを並べる用のFragment
 */
public class MultiFragment1 extends Fragment {
    public static final String TAG = "MainFragment";
    public static final String FRAMGMENT_NAME = MainFragment.class.getName();

    private final static String KEY_NAME = "key_name";
    private final static String KEY_BACKGROUND = "key_background_color";

    // 引数
    private String mName;
    private int mBackgroundColor;

    // Views
    TextView textView;
    /**
     * このメソッドからFragmentを作成することを強制する
     */
    @CheckResult
    public static MultiFragment1 createInstance(String name, int color) {

        // Fragmentを作成して返すメソッド
        MultiFragment1 fragment = new MultiFragment1();

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

        Log.d(TAG, "onCreate");
        // Bundleの値を受け取る際はonCreateメソッド内で行う
        // Bundleがセットされていなかった時はNullなのでNullチェックをする
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
        return inflater.inflate(R.layout.fragment_multi_fragment1, container, false);
    }

    /**
     * Viewが生成された後に呼ばれる
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");

        // 引数のテキストを表示
        textView = (TextView)view.findViewById(R.id.textView);
        textView.setText(mName);
        // 背景色を設定
        ((ViewGroup)view.findViewById(R.id.bg_layout)).setBackgroundColor(mBackgroundColor);
    }

}
