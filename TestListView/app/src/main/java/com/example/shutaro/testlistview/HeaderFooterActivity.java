package com.example.shutaro.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HeaderFooterActivity extends AppCompatActivity {

    private static final String[] DAYS = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);

        View header = (View)getLayoutInflater().inflate(R.layout.list_header_item,null);
        View footer = (View)getLayoutInflater().inflate(R.layout.list_footer_item,null);

        // ヘッダーとフッターを追加
        ListView lv = (ListView)findViewById(R.id.listView2);
        lv.addHeaderView(header);
        lv.addFooterView(footer);

        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Adapterの作成
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DAYS);
        // Adapterの設定
        lv.setAdapter(adapter);

//        View header2 = (View)getLayoutInflater().inflate(R.layout.list_header_item,null);
//        LinearLayout linear1 = (LinearLayout)findViewById(R.id.header_activity_top);
//        linear1.addView(header2);
    }
}
