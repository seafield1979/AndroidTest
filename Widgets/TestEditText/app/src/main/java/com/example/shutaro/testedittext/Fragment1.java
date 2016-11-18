package com.example.shutaro.testedittext;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * EditText編集中のイベントを取得
 *
 * beforeTextChanged
 *　onTextChanged
 *　afterTextChanged
 */
public class Fragment1 extends Fragment {
    public static final String FRAMGMENT_NAME = Fragment1.class.getName();
    public static final String TAG = "Fragment1";

    private static final int[] editIds = {
            R.id.editText,
            R.id.editText2,
            R.id.editText3,
            R.id.editText4
    };
    private AutoCompleteTextView autoComplete;

    // サジェストの候補
    static final String[] COUNTRIES = new String[] {
            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
            "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
            "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        for (int id : editIds) {
            ((EditText)view.findViewById(id)).addTextChangedListener(watchHandler);
        }
        // サジェストのテキストを設定する
        autoComplete = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout
                .simple_dropdown_item_1line, COUNTRIES);
        autoComplete.setAdapter(adapter);


        // キーボードのON/OFFイベントの検出
        DetectableKeyboardEventLayout root = (DetectableKeyboardEventLayout)view.findViewById(R.id
                .root);
        root.setKeyboardListener( new DetectableKeyboardEventLayout.KeyboardListener() {
            @Override
            public void onKeyboardShown() {
                Log.d(TAG, "keyboard shown");
            }
            @Override
            public void onKeyboardHidden() {
                Log.d(TAG, "keyboard hidden");
            }
        });
        return view;
    }

    private TextWatcher watchHandler = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged() s:" + s.toString() + " start:" + String.valueOf(start) + " count:" + String.valueOf(count) +
                    " after:" + String.valueOf(after));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged() s:" + s.toString() + " start:" + String.valueOf(start) + " before:" + String.valueOf(before) +
                    " count:" + String.valueOf(count));
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged()");
        }
    };

}