package com.example.shutaro.testfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ただのFragment
 */
public class Fragment2 extends Fragment {
    public static final String FRAMGMENT_NAME = Fragment2.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment22, container, false);
        return view;
    }
}
