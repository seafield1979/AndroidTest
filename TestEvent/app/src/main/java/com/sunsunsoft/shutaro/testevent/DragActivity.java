package com.sunsunsoft.shutaro.testevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DragActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    private ImageView mImageView;
    private View mDragView;
    private RelativeLayout mContainer;
    private TextView mTextView;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        mContainer = (RelativeLayout)findViewById(R.id.container1);
        mImageView = (ImageView)findViewById(R.id.imageView2);
        mTextView = (TextView)findViewById(R.id.textView);
        mScrollView = (ScrollView)findViewById(R.id.scrollView1);

        // イベントリスナを設定
        mContainer.setOnDragListener(this);
        mImageView.setOnTouchListener(this);
    }

    /**
     * タッチイベント
     */
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
        }
        return true;
    }

    /**
     * ドラッグイベント
     */
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
                Log.v("mylog", "x:" + e.getX() + " y:" + e.getY());
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

        // 画面いっぱいになったらクリア
        if (mTextView.getHeight() > mScrollView.getHeight()) {
            mTextView.setText("");
        }

        mTextView.append(action + "\n");

        return true;
    }
}
