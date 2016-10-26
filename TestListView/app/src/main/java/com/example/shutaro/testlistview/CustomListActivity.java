package com.example.shutaro.testlistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CustomListActivity extends AppCompatActivity implements OnItemClickListener {
    public static final int ITEM_MAX = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        // Adapterに設定するCustomData配列を作成する
        Bitmap image;
        image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        List<CustomData> objects = new ArrayList<CustomData>();

        for (int i=0; i<ITEM_MAX; i++) {
            CustomData item = new CustomData();
            item.setImagaData(image);
            item.setTextData(String.valueOf(i+1));
            objects.add(item);
        }

        // カスタムしたAdapterを作成
        CustomAdapter customAdapater = new CustomAdapter(this, 0, objects);

        // Apadpterを設定
        ListView listView = (ListView) findViewById(R.id.customListView);
        listView.setAdapter(customAdapater);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView)parent;
        CustomData data = (CustomData)lv.getItemAtPosition(position);
        Log.v("myLog", data.getTextData());
        makeToast(data.getTextData(), 0, 0);
    }

    // Toast を表示する
    // x,y はデフォルトの表示位置(画面中央)からのオフセット
    private void makeToast(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, x, y);
        toast.show();
    }
}
