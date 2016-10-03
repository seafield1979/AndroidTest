package com.example.shutaro.testlistview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewActivity extends Activity implements OnItemClickListener {

    public static final String[] WEEK = new String[]{"月","火","水","木","金","土","日","月","火","水","木","金","土","日","月","火","水","木","金","土","日","月","火","水","木","金","土","日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView grid1 = (GridView)findViewById(R.id.gridView);
        grid1.setOnItemClickListener(this);

        // Adapter作成
        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, WEEK);
        // Adapter設定
        grid1.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GridView gv = (GridView)parent;
        Log.v("myLog", (String)gv.getItemAtPosition(position));
    }

}
