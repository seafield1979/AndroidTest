package com.sunsunsoft.shutaro.testdragdrop;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements OnDragListener, OnLongClickListener {

    private ViewGroup mContainer;
    private View mDragView;
    private ImageView mInsertPosView;
    private int insertPos;
    private int startPos;

    private ImageView[] imageViews = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = (ViewGroup)findViewById(R.id.container1);

        imageViews[0] = (ImageView)findViewById(R.id.imageView);
        imageViews[0].setBackgroundColor(Color.rgb(255,0,0));
        imageViews[1] = (ImageView)findViewById(R.id.imageView2);
        imageViews[1].setBackgroundColor(Color.rgb(0,255,0));
        imageViews[2] = (ImageView)findViewById(R.id.imageView3);
        imageViews[2].setBackgroundColor(Color.rgb(255,0,255));
        imageViews[3] = (ImageView)findViewById(R.id.imageView4);
        imageViews[3].setBackgroundColor(Color.rgb(255,255,0));

        for (int i=0; i<imageViews.length; i++) {
            imageViews[i].setOnLongClickListener(this);
        }

        mInsertPosView = new ImageView(this);
        ViewGroup.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.height = 144;
        mInsertPosView.setLayoutParams(layout);

        mContainer.setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        mDragView = v;
        mDragView.startDrag(null, new View.DragShadowBuilder(v), null, 0);
        mContainer.removeView(mDragView);
        mContainer.removeView(mInsertPosView);

        for (int i=0; i<mContainer.getChildCount(); i++) {
            if (mContainer.getChildAt(i) == mDragView) {
                startPos = i;
                break;
            }
        }

        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        boolean result = false;
        switch(event.getAction()) {
            case DragEvent.ACTION_DROP: // ドロップ時に呼び出し
            {
                Log.v("myLog", "" + event.getY());
                int pos = getDropPosition(event.getX(), event.getY() - 72);
                mContainer.removeView(mDragView);
                mContainer.addView(mDragView, pos);
                mContainer.removeView(mInsertPosView);
            }
                break;
            case DragEvent.ACTION_DRAG_STARTED: { //ドラッグ開始時に呼び出し
                Log.i("DragSample", "Drag started, event=" + event);

                result = true;
            } break;

            case DragEvent.ACTION_DRAG_ENDED: { //ドラッグ終了時に呼び出し
                Log.i("DragSample", "Drag ended.");
                boolean foundFlag = false;
                for (int i=0; i<mContainer.getChildCount(); i++) {
                    if (mContainer.getChildAt(i) == mDragView) {
                        foundFlag = true;
                        break;
                    }
                }
                if (!foundFlag) {
                    mContainer.addView(mDragView, startPos);
                    mContainer.removeView(mInsertPosView);
                }
            } break;

            case DragEvent.ACTION_DRAG_LOCATION: { //ドラッグ中に呼び出し

                int pos = getDropPosition(event.getX(), event.getY() - 72);
                Log.i("DragSample", ""+ event.getY() + " " + pos);
                if (insertPos != pos) {
                    insertPos = pos;
                    mContainer.removeView(mInsertPosView);
                    mContainer.addView(mInsertPosView, pos);
                }
                result = true;
            } break;

            case DragEvent.ACTION_DRAG_ENTERED: {//ドラッグ開始直後に呼び出し
                Log.i("DragSample", "Entered  " + this);
                result = true;
            } break;

            case DragEvent.ACTION_DRAG_EXITED: {//ドラッグ終了直前に呼び出し
                Log.i("DragSample", "Exited  " + this);
                result = true;
            } break;

            default:

                Log.i("DragSample", "other drag event: " + event);
                result = true;
                break;
        }
        return result;
    }


    private int getDropPosition(float x, float y) {
        int pos = 0;
        for (int i=0; i<mContainer.getChildCount(); i++) {
            if (mContainer.getChildAt(i) instanceof ImageView) {
                View v = mContainer.getChildAt(i);
                if (v.getTop() >= y) {
                    Log.v("myLog", "drop:" + i + " " + v.getY() + " " + y);
                    return i;
                }
                pos++;
            }
        }
        return imageViews.length - 1;
    }
}
