package com.example.shutaro.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener{

    private ListView listView;
    private Button addButton;
    private Button deleteButton;
    private Button updateButton;
    private ArrayAdapter<String> mAdapter;
    private LinkedList<String> mList = new LinkedList<>();
    private int addCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(String day : DAYS) {
            mList.add(day);
        }

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        // Adapterの作成
        // チェックボックスなし
//        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DAYS);

        // チェックボックスあり
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mList);

        // Adapterの設定
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        // Button
        addButton = (Button)findViewById(R.id.button);
        addButton.setOnClickListener(this);

        deleteButton = (Button)findViewById(R.id.button2);
        deleteButton.setOnClickListener(this);

        updateButton = (Button)findViewById(R.id.button3);
        updateButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.button:
            {
//                ArrayAdapter<String> adapter = (ArrayAdapter<String>)listView.getAdapter();
                mAdapter.add("hoge"+addCount);
                addCount++;
                listView.setAdapter(mAdapter);
            }
                break;
            case R.id.button2:
            {
                // チェックがついた項目を削除する
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++) {
                    int at = checked.keyAt(i);
                    if (checked.get(at)) {

                        mAdapter.remove(listView.getItemAtPosition(at).toString());
//                        Log.d("example", "選択されている項目:" + );
//                        Log.d("example", "そのキー" + at);
                    }
                }
//                mList.
            }
                break;
            case R.id.button3:
            {

            }
                break;
        }
    }

    /**
     * チェックをクリアする
     */
    private void clearChecked() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView)parent;
        Log.v("myLog", (String)lv.getItemAtPosition(position));
    }


        private static final String[] DAYS = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

}
