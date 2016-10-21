package com.sunsunsoft.shutaro.testevent;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyFragment4 extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {
    private final static String BACKGROUND_COLOR = "background_color";

    private Button mButton;
    private TextView mTextView;
    private ImageView[] mImageView = new ImageView[2];
    private View mDragView;
    private FrameLayout mContainer;
    private ScrollView mScrollView;

    public static MyFragment4 newInstance(@ColorRes int IdRes) {
        MyFragment4 frag = new MyFragment4();
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
        View view = inflater.inflate(R.layout.fragment_page4, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page4_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        mContainer = (FrameLayout)view.findViewById(R.id.container1);
        mButton = (Button)view.findViewById(R.id.button);
        mTextView = (TextView)view.findViewById(R.id.textView3);
        mImageView[0] = (ImageView)view.findViewById(R.id.imageView1);
        mImageView[1] = (ImageView)view.findViewById(R.id.imageView2);
        mScrollView = (ScrollView)view.findViewById(R.id.scrollView1);

        // イベントリスナを設定
        mContainer.setOnDragListener(this);
        mButton.setOnClickListener(this);

        for (ImageView iv : mImageView) {
            iv.setOnTouchListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        ViewGroup.MarginLayoutParams layout;

        layout = (ViewGroup.MarginLayoutParams)mImageView[0].getLayoutParams();
        mTextView.append("x:" + layout.leftMargin + " y:" + layout.topMargin + " w:" +  mImageView[0].getWidth() + " h:" +  mImageView[0].getHeight());

        for (int i=0; i<mImageView.length; i++) {
            ImageView iv = mImageView[i];
            layout = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
            layout.leftMargin = 70 * i;
            layout.topMargin = 0;
            iv.setLayoutParams(layout);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e){
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDragView = v;
                mDragView.startDrag(null, new View.DragShadowBuilder(v), null, 0);
            }
            break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE: {
                Log.v("mylog", "x:" + e.getX() + " y:" + e.getY());
            }
            break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
        return true;
    }

    public boolean onDrag(View v, DragEvent e) {
        String action = "";
        switch(e.getAction()) {
            case DragEvent.ACTION_DROP: // ドロップ時
                action = "ACTION_DROP";
            {
                // ImageViewをドロップ先に移動
                ViewGroup.MarginLayoutParams layout = (ViewGroup.MarginLayoutParams)mDragView.getLayoutParams();
                layout.leftMargin = (int)e.getX() - mDragView.getWidth()/2;
                layout.topMargin = (int)e.getY() - mDragView.getHeight()/2;
                mDragView.setLayoutParams(layout);
            }    break;

            case DragEvent.ACTION_DRAG_ENDED: //ドラッグ終了
                action = "ACTION_DRAG_ENDED";

                break;

            case DragEvent.ACTION_DRAG_LOCATION: //ドラッグ中
                //action = "ACTION_DRAG_LOCATION";
            {
                float dx = e.getX();
                float dy = e.getY();

                for (ImageView iv : mImageView) {

                    if (iv.getId() != mDragView.getId()) {
                        ViewGroup.MarginLayoutParams layout2 = (ViewGroup.MarginLayoutParams)iv.getLayoutParams();
                        float x = layout2.leftMargin + mDragView.getWidth()/2;
                        float y = layout2.topMargin + mDragView.getHeight()/2;
                        float w = iv.getWidth() / 2;
                        float h = iv.getHeight() / 2;

                        // 重なり判定(距離)
                        float d1 = (dx - x) * (dx - x) + (dy - y) * (dy - y);
                        float d2 = w*w+h*h;
                        if ( d1 < d2 ) {
                            Log.v("myLog","overlapping " + d1 + " " + d2);
//                            mTextView.append("overlapping\n");

                            iv.setBackgroundColor(Color.rgb(255,255,255));
                        } else {
                            iv.setBackgroundColor(Color.argb(0,0,0,0));

                        }
                    }
                }
            }
                break;

            case DragEvent.ACTION_DRAG_STARTED:  //ドラッグ開始時
                action = "ACTION_DRAG_STARTED";
                break;

            case DragEvent.ACTION_DRAG_ENTERED: //ドラッグ開始直後
                action = "ACTION_DRAG_ENTERED";
                break;

            case DragEvent.ACTION_DRAG_EXITED: //ドラッグ終了直前
                action = "ACTION_DRAG_EXITED";
                break;

            default:
        }
        // 画面いっぱいになったらクリア
        if (mTextView.getHeight() > mScrollView.getHeight()) {
            mTextView.setText("");
        }
        if (action.length() > 0) {
            mTextView.append(action + "\n");
        }
        return true;
    }
}
