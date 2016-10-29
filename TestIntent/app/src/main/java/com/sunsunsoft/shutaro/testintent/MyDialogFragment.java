package com.sunsunsoft.shutaro.testintent;


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


public class MyDialogFragment extends DialogFragment {
    int mNum;

    EditText edit1;

    // 選択されたラジオボタンのID
    int mCheckedId;
    private OnOkClickListener mListener;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static MyDialogFragment newInstance(int num) {
        MyDialogFragment f = new MyDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public interface OnOkClickListener {
        public void onOkClicked(Bundle args);
    }

//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        mListener = (OnOkClickListener) getTargetFragment();
//        if (mListener instanceof OnOkClickListener == false) {
//            throw new ClassCastException("実装エラー");
//        }
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

        mListener = (OnOkClickListener) getTargetFragment();
        if (mListener instanceof OnOkClickListener == false) {
            throw new ClassCastException("実装エラー");
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 引数を取得
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
//        int style = DialogFragment.STYLE_NORMAL, theme = 0;
//        switch ((mNum-1)%6) {
//            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
//            case 4: style = DialogFragment.STYLE_NORMAL; break;
//            case 5: style = DialogFragment.STYLE_NORMAL; break;
//            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 8: style = DialogFragment.STYLE_NORMAL; break;
//        }
//        switch ((mNum-1)%6) {
//            case 4: theme = android.R.style.Theme_Holo; break;
//            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
//            case 6: theme = android.R.style.Theme_Holo_Light; break;
//            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
//            case 8: theme = android.R.style.Theme_Holo_Light; break;
//        }
//        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        View tv = v.findViewById(R.id.text);
        ((TextView)tv).setText("Dialog #" + mNum + ": using style "
                + mNum);

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit();
            }
        });

        edit1 = (EditText)v.findViewById(R.id.editText);


        return v;
    }

    private void submit() {
        Bundle arg = new Bundle();
        arg.putString("ret", "hello again");
        mListener.onOkClicked(arg);
        dismiss();
    }
}