package com.example.shutaro.testedittext;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.EditText.Text

/**
 * EditTextのイベントを取得するサンプル
 */
public class Fragment3 extends Fragment {

    public static final String TAG = "Fragment3";

    private EditText mEditText;
    private TextView mTextView;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment3, container, false);

        // EditText
        mEditText = (EditText)view.findViewById(R.id.editText);
        mEditText.addTextChangedListener(watchHandler);

        // TextView
        mTextView = (TextView)view.findViewById(R.id.textView);


        return view;
    }

    /**
     * EditTextの入力情報をリアルタイムで取得する
     */
    private TextWatcher watchHandler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String str = "beforeTextChanged() s:" +
                    s.toString() +
                    "\n start:" + String.valueOf(start) +
                    "\n count:" + String.valueOf(count) +
                    "\n after:" + String.valueOf(after);
            mTextView.setText(str);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = "onTextChanged() s:" +
                    s.toString() +
                    "\n start:" + String.valueOf(start) +
                    "\n before:" + String.valueOf(before) +
                    "\n count:" + String.valueOf(count);
            mTextView.setText(str);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = "afterTextChanged()";
            mTextView.setText(str);
        }
    };
}
