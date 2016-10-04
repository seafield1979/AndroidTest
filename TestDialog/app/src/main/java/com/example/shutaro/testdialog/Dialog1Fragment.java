package com.example.shutaro.testdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by shutaro on 2016/10/04.
 * タイトル、メッセージ、OKボタン、キャンセルボタンを表示するダイアログ
 */
public class Dialog1Fragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        boolean isCancel = bundle.getBoolean("isCancel");

        // Add action buttons
        // Title
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        // OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 呼び出し元のActivityはgetActivity()で取得
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.onReturnValue("Dialog1Fragment OK");
            }
        });

        // Cancel
        if (isCancel) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Dialog1Fragment.this.getDialog().cancel();
                }
            });
        }
        builder.setNeutralButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Later button pressed
            }
        });

        return builder.create();
    }
}