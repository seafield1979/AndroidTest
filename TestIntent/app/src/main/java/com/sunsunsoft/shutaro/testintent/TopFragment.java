package com.sunsunsoft.shutaro.testintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements OnClickListener, MyDialogFragment.OnOkClickListener {

    EditText editArgs;
    TextView textRet;
    public static final int REQUEST_CODE_1 = 1;
    public static final int REQUEST_CODE_2 = 2;
    public static final String FRAMGMENT_NAME = "TopFragment";
    public static int mStackLevel;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        editArgs = (EditText)view.findViewById(R.id.editText1);
        textRet = (TextView)view.findViewById(R.id.textView1);

        ((Button)view.findViewById(R.id.button)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.button2)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.button3)).setOnClickListener(this);

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
                showDialog();
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

    private void startFragment() {
        // データを渡す為のBundleを生成し、渡すデータを内包させる
//        Bundle bundle = new Bundle();
//        bundle.putString("str1", "param from TopFragment");
//
//        // Fragmentを生成し、setArgumentsで先ほどのbundleをセットする
//        FragmentA fragment = new FragmentA();
//        fragment.setArguments(bundle);
//
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        // コンテナにMainFragmentを格納
//        transaction.replace(R.id.fragment_container, fragment, FragmentA.FRAMGMENT_NAME);
//        // backstackに追加
//        transaction.addToBackStack(null);
//        // 画面に表示
//        transaction.commit();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentA fragment = new FragmentA();

        Bundle bundle = new Bundle();
        bundle.putString("str1", "param from TopFragment");
        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = MyDialogFragment.newInstance(mStackLevel);
        newFragment.setTargetFragment(TopFragment.this, 0);
        newFragment.show(ft, "dialog");
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
                if (resultCode == Activity.RESULT_OK) {
                    String ret = data.getStringExtra("str1");
                    textRet.setText(ret);
                }
                break;
            case REQUEST_CODE_2:
                if (resultCode == Activity.RESULT_OK) {
                    String ret = data.getStringExtra(Intent.EXTRA_TEXT);
                    textRet.setText(ret);
                }
                break;
        }
    }
    // コールバックされるメソッド
    @Override
    public void onOkClicked(Bundle args) {
        String retStr = args.getString("ret");
        textRet.setText(retStr);
    }
}
