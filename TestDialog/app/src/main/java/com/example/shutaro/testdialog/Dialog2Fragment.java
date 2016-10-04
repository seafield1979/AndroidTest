package com.example.shutaro.testdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by shutaro on 2016/10/04.
 * タイトル、メッセージ、OKボタン、キャンセルボタンを表示するダイアログ
 */
public class Dialog2Fragment extends DialogFragment {

    final String[] items = {"item_0", "item_1", "item_2"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String title = bundle.getString("title");

        // Add action buttons
        // Title
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // item_which pressed
                Log.v("myLog", "Dialog2 " + String.valueOf(which));
            }
        });

        return builder.create();
    }
}