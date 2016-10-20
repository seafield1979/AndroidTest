package com.sunsunsoft.shutaro.testevent;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ドラッグイベント
 * ImageViewをドラッグして移動
 */
public class MyFragment3 extends Fragment implements OnClickListener, OnTouchListener, OnDragListener{
    private final static String BACKGROUND_COLOR = "background_color";

    private Button mButton;
    private TextView mTextView;
    private ImageView mImageView;
    private View mDragView;
    private RelativeLayout mContainer;

    public static MyFragment3 newInstance(@ColorRes int IdRes) {
        MyFragment3 frag = new MyFragment3();
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
        View view = inflater.inflate(R.layout.fragment_page3, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_page3_linearlayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        mContainer = (RelativeLayout)view.findViewById(R.id.container1);
        mButton = (Button)view.findViewById(R.id.button);
        mTextView = (TextView)view.findViewById(R.id.textView3);
        mImageView = (ImageView)view.findViewById(R.id.imageView2);

        // イベントリスナを設定
        mContainer.setOnDragListener(this);
        mButton.setOnClickListener(this);
        mImageView.setOnTouchListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ViewGroup.MarginLayoutParams layout = (ViewGroup.MarginLayoutParams)mImageView.getLayoutParams();
        layout.leftMargin = 50;
        layout.topMargin = 50;
        mImageView.setLayoutParams(layout);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e){

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                // ドラッグ開始
                mDragView = v;
                mDragView.startDrag(null, new View.DragShadowBuilder(v), null, 0);
            }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
            {
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
                action = "ACTION_DRAG_LOCATION";
                Log.v("mylog", "x:" + e.getX() + " y:" + e.getY());
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

        mTextView.setText(action);

        return true;
    }
}
