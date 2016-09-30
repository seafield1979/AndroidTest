package com.example.shutaro.test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("LifeCycle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // textView の表示を変更する
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText("てきすとびゅー1");
        TextView tv2 = (TextView)findViewById(R.id.textView2);
        String str = tv2.getText().toString();

        Log.v("myLog", str);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        // ボタンを押した時の処理を追加
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

        // ぼたん２
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                Intent i = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(i);
            }
        });

        // ぼたん３
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            // クリック時の処理
            // 別のActivityを起動
            Intent i = new Intent(MainActivity.this, Main4Activity.class);
            startActivity(i);
            }
        });

        // ぼたん４
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                // アプリ終了
                //moveTaskToBack(true);
                finish();
            }
        });

        // コードでボタンを追加
        button5 = new Button(this);
        button5.setText("ぼたん５");
        button5.setOnClickListener(this);

        // レイアウトを追加(ボタン３の下に配置)
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.BELOW, R.id.button3);
        layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        button5.setLayoutParams(layout);

        // ViewGroupに追加
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.addView(button5);
    }

    public void onClick(View v) {
        if (v == button5) {
            Log.v("myLog", "button5 pushed");
        }
    }

    // Toast を表示する
    private void toastMake(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, x, y);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.v("LifeCycle", "onStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("LifeCycle", "onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.v("LifeCycle", "onPause");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.v("LifeCycle", "onRestart");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.v("LifeCycle", "onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("LifeCycle", "onDestroy");
    }
}
