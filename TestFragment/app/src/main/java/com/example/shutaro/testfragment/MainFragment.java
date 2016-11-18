package com.example.shutaro.testfragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragmentの基本を確認するサンプル
 *
 * インスタンスはcreateInstanceで行う。createInstanceメソッドの引数がそのままFragmentの引数として使用される。
 * ライフサイクルメソッドのログを出力。これで起動、バックグラウンドに移動した時などのイベント呼び出しを確認できる。
 */
public class MainFragment extends Fragment implements OnClickListener, MyDialogFragment
        .OnOkClickListener, OnKeyListener{
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
    public static MainFragment createInstance(String name, int color) {
        Log.d(TAG,"createInstance1");

        // Fragmentを作成して返すメソッド
        MainFragment fragment = new MainFragment();

        Log.d(TAG,"createInstance2");

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

    /**
     * FragmentのViewが生成された
      */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView 1");

        View view = inflater.inflate(R.layout.fragment1, container, false);

        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);

        Log.d(TAG, "onCreateView 2");
        return view;
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

        ((Button)view.findViewById(R.id.buttonDialog)).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonDialog:
            {
                // DialogFragmentを表示する
                DialogFragment dialogFragment = MyDialogFragment.createInstance("hoge");
                dialogFragment.setTargetFragment(MainFragment.this, 0);
                dialogFragment.show(getFragmentManager(), "fragment_dialog");
            }
                break;
        }
    }

    // コールバックされるメソッド
    // OnOkClickListener インターフェース
    @Override
    public void onOkClicked(Bundle args) {
        if (args != null) {
            String retStr = args.getString(MyDialogFragment.KEY_RET);
            textView.append(retStr);
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
