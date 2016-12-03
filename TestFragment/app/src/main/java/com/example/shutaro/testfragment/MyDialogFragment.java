package com.example.shutaro.testfragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


interface OnOkClickListener {
    void onOkClicked(Bundle args);
}

/**
 * ダイアログ用のFragmentサンプル
 * DialogFragmentのサブクラス
 */
public class MyDialogFragment extends DialogFragment {
    private final static String KEY_NAME = "key_name";
    public static final String KEY_RET = "key_ret";

    String mName;

    EditText edit1;

    private OnOkClickListener mListener;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static MyDialogFragment createInstance(String name) {
        MyDialogFragment f = new MyDialogFragment();

        // set arguments
        Bundle args = new Bundle();
        args.putString(KEY_NAME, name);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            Fragment targetFragment = this.getTargetFragment();
            if (targetFragment == null) {
                mListener = (OnOkClickListener)context;
            } else {
                mListener = (OnOkClickListener) targetFragment;
            }
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Don't implement OnCustomDialogListener.");
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 引数を取得
        mName = getArguments().getString(KEY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView)view.findViewById(R.id.text);
        textView.setText(mName);

        Button button = (Button)view.findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit();
            }
        });

        edit1 = (EditText)view.findViewById(R.id.editText);

        setMyStyle(0,0);
    }

    /**
     * 呼び出し元に引数を返して終了
     */
    private void submit() {
        Bundle arg = new Bundle();
        String str = edit1.getText().toString() + "\n";
        arg.putString(MyDialogFragment.KEY_RET, str);
        mListener.onOkClicked(arg);
        dismiss();
    }

    /**
     * DialogFragmentのスタイルを設定する
     *
     * @param _style
     * @param _theme
     */
    private void setMyStyle(int _style, int _theme) {
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch (_style) {
            case 0: style = DialogFragment.STYLE_NO_TITLE; break;
            case 1: style = DialogFragment.STYLE_NO_FRAME; break;
            case 2: style = DialogFragment.STYLE_NO_INPUT; break;
            case 3: style = DialogFragment.STYLE_NORMAL; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NO_TITLE; break;
            case 6: style = DialogFragment.STYLE_NO_FRAME; break;
            case 7: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch (_theme) {
            case 0: theme = android.R.style.Theme_Holo; break;
            case 1: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 2: theme = android.R.style.Theme_Holo_Light; break;
            case 3: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 4: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }
}