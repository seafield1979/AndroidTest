package com.example.shutaro.testactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Resourcesクラスを使ってリソースを参照する
        Resources res = getResources();
        String titleString = res.getString(R.string.hoge_text);
        Log.v("myLog",titleString);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(R.string.hoge_text);
        textView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color
                .colorPrimary));

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button1) {
            // Main2Activity アクティビティを呼び出す
            Intent i = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(i);
        }
    }
}
