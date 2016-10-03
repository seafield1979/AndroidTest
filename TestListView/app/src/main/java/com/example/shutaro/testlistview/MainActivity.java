package com.example.shutaro.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list1 = (ListView)findViewById(R.id.listView);
        list1.setOnItemClickListener(this);

        // Adapterの作成
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DAYS);
        // Adapterの設定
        list1.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView)parent;
        Log.v("myLog", (String)lv.getItemAtPosition(position));
    }


        private static final String[] DAYS = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

}
