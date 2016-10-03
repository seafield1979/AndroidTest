package com.example.shutaro.testlistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomListActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        // Adapterに設定するCustomData配列を作成する
        Bitmap image;
        image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        List<CustomData> objects = new ArrayList<CustomData>();

        for (int i=0; i<10; i++) {
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
    }
}
