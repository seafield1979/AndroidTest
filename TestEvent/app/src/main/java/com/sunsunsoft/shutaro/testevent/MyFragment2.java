package com.sunsunsoft.shutaro.testevent;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.OnTouchListener;

/*
 * タッチイベント
 */

public class MyFragment2 extends Fragment implements OnTouchListener{
    private final static String BACKGROUND_COLOR = "background_color";

    private ImageView mImageView;
    private TextView mTextView;
    private ScrollView mScrollView;

    public static MyFragment2 newInstance(@ColorRes int IdRes) {
        MyFragment2 frag = new MyFragment2();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, IdRes);
        frag.setArguments(b);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page2_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));


        mTextView = (TextView)view.findViewById(R.id.textView2);
        mScrollView = (ScrollView)view.findViewById(R.id.scrollView1);

        mImageView = (ImageView)view.findViewById(R.id.imageView);
        mImageView.setOnTouchListener(this);

        return view;
    }


    /**
     * タッチイベント
     * @param v タッチされたView
     * @param e タッチイベント
     * @return true:下にいるViewにTouchEventを渡さない
     *         false:下にいるViewにTouchEventを渡す
     */
    @Override
    public boolean onTouch(View v, MotionEvent e){
        String action = "";

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                break;
            default:
                action = "" + e.getAction();
        }

        // 画面いっぱいになったらクリア
        if (mTextView.getHeight() > mScrollView.getHeight()) {
            mTextView.setText("");
        }

        String id = getActivity().getResources().getResourceEntryName(v.getId());
        mTextView.append("action:" + action + " id:" + id + " x:" + e.getX() + " y:" + e.getY() + "\n");
        return true;
    }
}
