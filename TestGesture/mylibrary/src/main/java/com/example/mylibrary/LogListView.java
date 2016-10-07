package com.example.mylibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
    import android.widget.ListView;

import java.util.LinkedList;

/**
 * Created by shutaro on 2016/10/07.
 */
public class LogListView extends ListView {
    private static int mLogCount = 0;
    private static boolean mLogReverse = true;
    private static final int LOG_MAX = 10;
    private LinkedList<String> mLinkedList;

    public LogListView(Context context) {
        super(context);
    }
    public LogListView(Context context, AttributeSet attr) {
        super(context, attr);

        mLinkedList = new LinkedList<String>();
    }

    /**
     * ListViewに表示するログを追加する。古いログは自動的に削除される
     * @param message
     */
    public void addLog(String message) {
        String addMsg = String.format("%d %s", mLogCount, message);

        if (mLogReverse) {
            if (mLinkedList.size() < LOG_MAX) {
                mLinkedList.push(addMsg);
            } else {
                mLinkedList.removeLast();
                mLinkedList.push(addMsg);
            }
        } else {
            if (mLinkedList.size() < LOG_MAX) {
                mLinkedList.add(addMsg);
            } else {
                mLinkedList.poll();
                mLinkedList.add(addMsg);
            }
        }

        ArrayAdapter adapter = (ArrayAdapter)this.getAdapter();
        if (adapter != null) {
            adapter.clear();
            for (String str : mLinkedList) {
                adapter.add(str);
            }
        }
        mLogCount++;
    }
}
