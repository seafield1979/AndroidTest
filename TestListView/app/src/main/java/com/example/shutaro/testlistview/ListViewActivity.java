package com.example.shutaro.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import java.util.LinkedList;


/**
 * シンプルなListView
 */
public class ListViewActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener{
    private static final String[] DAYS = new String[]{
        "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
    };
    private ArrayAdapter<String> mAdapter;

    private LinkedList<String> mList = new LinkedList<>();
    private ListView listView;
    private static int addCount;
    private Button[] buttons = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // LinkedList初期化
        // ListViewの項目を変更したい場合は List をAdaptする
        for (String day : DAYS) {
            mList.add(day);
        }

        listView = (ListView)findViewById(R.id.listView);

        // Adapterの作成
        // チェックボックスなし
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                mList);
        // Adapterの設定
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);


        // Button
        buttons[0] = (Button)findViewById(R.id.button);
        buttons[1] = (Button)findViewById(R.id.button2);
        buttons[2] = (Button)findViewById(R.id.button3);

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    /**
     * ボタンのイベント
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // 項目を追加する
            {
                mAdapter.add("hoge"+addCount);
                addCount++;
                listView.setAdapter(mAdapter);
            }
                break;
            case R.id.button2:
                // 先頭の項目を削除する
            {
                String item = (String)mAdapter.getItem(0);
                if (item != null) {
                    mAdapter.remove(item);
                }
            }
                break;
            case R.id.button3:
                // 挿入
            {
                mAdapter.insert("hoge"+addCount, 0);
                addCount++;
            }
                break;
        }
    }

    /**
     * ListViewの項目が選択されたときのイベント
     * @param parent  ListViewのAdapter
     * @param view
     * @param position  項目の番号 0,1,2...
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView) parent;
        Log.v("myLog", "pos:" + position + " id:" + id + " " + (String) lv.getItemAtPosition
                (position));

    }
}
