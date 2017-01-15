package com.sunsunsoft.shutaro.testdialogfragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by shutaro on 2016/12/24.
 */

public class TestDialogFragment extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.base_dialog_fragment);
        // DialogFragmentのレイアウトを縦横共に全域まで広げます
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

}