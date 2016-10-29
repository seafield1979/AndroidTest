package com.sunsunsoft.shutaro.testintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment implements OnClickListener{
    public static String FRAMGMENT_NAME = "FragmentA";
    public static final int REQUEST_CODE_1 = 1;
    public static final int REQUEST_CODE_2 = 2;

    EditText editArgs;      // 呼び出し先に渡すパラメータ
    TextView textCome;      // 呼び出された時のパラメータ
    TextView textBack;      // 呼び出し元から戻った時のパラメータ

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        editArgs = (EditText)view.findViewById(R.id.editText1);
        textCome = (TextView)view.findViewById(R.id.textView1);
        textBack = (TextView)view.findViewById(R.id.textView2);

        ((Button)view.findViewById(R.id.button)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.button2)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.button3)).setOnClickListener(this);

        // 引数を取得する
        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String str1 = bundle.getString("str1");
        textCome.setText(str1);

        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                startActivity();
                break;
            case R.id.button2:
                startFragment();
                break;
            case R.id.button3:
                submit();
                break;
        }
    }

    /**
     * アクティビティを起動する
     */
    private void startActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), Main2Activity.class);
        intent.putExtra("str1", editArgs.getText().toString());
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    /**
     * フラグメントを起動する
     */
    private void startFragment() {
        // データを渡す為のBundleを生成し、渡すデータを内包させる
        Bundle bundle = new Bundle();
        bundle.putString("str1", "param from FragmentA");

        // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする
        FragmentA fragment = new FragmentA();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // コンテナにMainFragmentを格納
        transaction.replace(R.id.fragment_container, fragment, FragmentA.FRAMGMENT_NAME);
        // backstackに追加(戻るボタンで呼び出し元に戻る)
        transaction.addToBackStack(null);
        // 画面に表示
        transaction.commit();
    }

    /**
     * 戻る(戻り値あり)
     */
    private void submit() {
//        Fragment target = getParentFragment();
//        int requestCode = getTargetRequestCode();
//        if (target == null) { return; }
//
//        Intent data = new Intent();
//        data.putExtra(Intent.EXTRA_TEXT, "return from FragmentA");
//        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);


        FragmentManager manager = getFragmentManager();
        manager.popBackStack();
    }

    /**
     * 他のActivityの戻り値を取得する
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_1:
                if (resultCode == RESULT_OK) {
                    String ret = data.getStringExtra("str1");
                    textBack.setText(ret);
                }
                break;
            case REQUEST_CODE_2:
                if (resultCode == RESULT_OK) {
                }
                break;
        }
    }
}
