package com.example.shutaro.testlistview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class AutoAddListActivity extends AppCompatActivity implements OnScrollListener {
    private ArrayAdapter<String> mAdapter;
    private AsyncTask<String, Void, String> mTask;
    private ListView mListView;
    private View mFooter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_add_list);

        // 最初に表示する項目
        mListView = (ListView) findViewById(R.id.listView3);

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        for (int i = 1; i < 10; i++) {
            mAdapter.add("Item" + i);
        }

        // 読み込み中のフッターを生成
        mFooter = getLayoutInflater().inflate(R.layout.list_progress_item, null);
        // ListViewのフッターに設定
        mListView.addFooterView(mFooter);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
    }


    /**
     * ListViewのスクロールイベント
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (totalItemCount == firstVisibleItem + visibleItemCount) {
            additionalReading();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }

    /**
     * 項目を追加読み込みする
     */
    private void additionalReading() {
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }
        /*
            ネットワークやファイルからデータを読み込むすることを考慮して非同期の読み込み処理
         */
        mTask = new MyAsyncTask(this).execute("text");
    }

    /**
     * 非同期処理を行うクラス
     */
    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        public MyAsyncTask(AutoAddListActivity androidAsyncTaskActivity) {
        }
        // メインスレッド以外のスレッドで実行
        // sleepで擬似的な処理時間を発生させている
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return params[0];
        }

        // doInBackground()処理後に呼ばれる
        @Override
        protected void onPostExecute(String text) {
            for (int n = 0; n < 10; n++) {
                mAdapter.add(text + n);
            }
        }
    }
}
