package com.example.shutaro.testdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shutaro on 2016/10/04.
 * タイトル、メッセージ、OKボタン、キャンセルボタンを表示するダイアログ
 */
public class Dialog3Fragment extends DialogFragment {

    final String[] items = {"item_0", "item_1", "item_2"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();

        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String title = bundle.getString("title");

        // Add action buttons
        // Title
        if (title != null) {
            builder.setTitle(title);
        }

        // OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 呼び出し元のActivityはgetActivity()で取得
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.onReturnValue(checkedItems);
            }
        });

        // Checked list
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkedItems.add(which);
                }
                else {
                    checkedItems.remove((Integer) which);
                }
            }
        });

        return builder.create();
    }
}