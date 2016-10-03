package com.example.shutaro.testlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MenuActivity extends AppCompatActivity implements OnItemSelectedListener{

    public static final String[] menuItems = new String[]{
            "---",
            "List",
            "Grid",
            "Header&Footer",
            "Custom",
            "Expand"
    };

    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // プログラムで項目を追加する
        spinner1 = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout
                .simple_spinner_dropdown_item);
        for (String menu : menuItems) {
            adapter.add(menu);
        }
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) parent.getSelectedItemId()) {
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

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
