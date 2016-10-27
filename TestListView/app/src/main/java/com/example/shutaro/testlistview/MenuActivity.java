package com.example.shutaro.testlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends AppCompatActivity implements OnItemClickListener{

    public static final String[] menuItems = new String[]{
            "ListView",
            "ListView2",
            "Grid",
            "Header&Footer",
            "Custom",
            "Expand",
            "Auto Add"
    };
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = (ListView)findViewById(R.id.listView);

        // Adapterの作成
        // チェックボックスなし
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        // Adapterの設定
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView)parent;
        Log.v("myLog", (String)lv.getItemAtPosition(position));

        switch (position) {
            case 0: {
                Intent i = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(i);
            }
            break;
            case 1: {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            break;

            case 2: {
                Intent i = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(i);
            }
            break;
            case 3: {
                Intent i = new Intent(getApplicationContext(), HeaderFooterActivity.class);
                startActivity(i);
            }
            break;
            case 4: {
                Intent i = new Intent(getApplicationContext(), CustomListActivity.class);
                startActivity(i);
            }
            break;
            case 5: {
                Intent i = new Intent(getApplicationContext(), ExpandListActivity.class);
                startActivity(i);
            }
            break;
            case 6: {
                Intent i = new Intent(getApplicationContext(), AutoAddListActivity.class);
                startActivity(i);
            }
            break;

        }
    }
}
