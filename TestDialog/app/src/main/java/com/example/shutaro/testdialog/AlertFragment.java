package com.example.shutaro.testdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by shutaro on 2016/10/04.
 */
public class AlertFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // 渡された引数を取得する
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String message = bundle.getString("message");

        // layoutファイルから自前のViewを取得
        final View layout = inflater.inflate(R.layout.dialog_contents, null);

        TextView textView = (TextView)layout.findViewById(R.id.messageTextView);
        if (message != null) {
            textView.setText(message);
        }

        builder.setView(layout);

        // Add action buttons
        // Title
        if (title != null) {
            builder.setTitle(title);
        }
        // OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // エディットの入力文字列を取得する
                        EditText text = (EditText) layout.findViewById(R.id.edit_text);
                        String string = text.getText().toString();

                        // 呼び出し元のActivityはgetActivity()で取得
                        MainActivity callingActivity = (MainActivity) getActivity();
                        callingActivity.onReturnValue(string);
                    }
                });
        // Cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AlertFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}