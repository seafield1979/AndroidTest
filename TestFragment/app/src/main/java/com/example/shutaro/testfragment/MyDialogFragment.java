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

    public interface OnOkClickListener {
        void onOkClicked(Bundle args);
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
    }

    private void submit() {
        Bundle arg = new Bundle();
        String str = edit1.getText().toString() + "\n";
        arg.putString(MyDialogFragment.KEY_RET, str);
        mListener.onOkClicked(arg);
        dismiss();
    }
}