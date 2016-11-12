package com.sunsunsoft.shutaro.testsurfaceview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    public static final String FRAMGMENT_NAME = Fragment1.class.getName();
    public static final int mode = 2;

    public Fragment1() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        LinearLayout viewContainer = (LinearLayout)view.findViewById(R.id.view_container);

        // SurfaceViewを生成
        // 画面に表示
        if (mode == 1) {
            MySurfaceView surfaceView = new MySurfaceView(getContext());
            viewContainer.addView(surfaceView);
        } else {
            MySurfaceView2 surfaceView = new MySurfaceView2(getContext());
            viewContainer.addView(surfaceView);
        }
        return view;
    }

}
