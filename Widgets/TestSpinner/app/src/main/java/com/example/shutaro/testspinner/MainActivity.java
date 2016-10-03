package com.example.shutaro.testspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner)findViewById(R.id.spinner);

        // 項目が選択された時の処理
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getSelectedItem();
                String str = String.format("%s (%d)が選択されました", item, parent.getSelectedItemId());
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // プログラムで項目を追加する
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout
                .simple_spinner_dropdown_item);
        adapter.add("hoge1");
        adapter.add("hoge2");
        adapter.add("hoge3");
        adapter.add("hoge4");
        adapter.add("hoge5");
        spinner2.setAdapter(adapter);

    }
}
