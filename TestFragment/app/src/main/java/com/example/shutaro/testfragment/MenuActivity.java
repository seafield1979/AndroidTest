package com.example.shutaro.testfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * メニュー画面
 * DialogFragmentを使用するために OnOkClickListener を implementsしている
 */
public class MenuActivity extends AppCompatActivity implements MyDialogFragment.OnOkClickListener {

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
            {
//                Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(i);
            }
                break;
            case R.id.button2:
            {
                Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(i);
            }
                break;
            case R.id.button3:
            {
                Intent i = new Intent(getApplicationContext(),Main3Activity.class);
                startActivity(i);
            }
                break;
            case R.id.button4:
            {
                Intent i = new Intent(getApplicationContext(),BasicFragmentActivity.class);
                startActivity(i);
            }
                break;
            case R.id.button5:
            {
                // ActivityからDialogFragmentを使用する
                DialogFragment dialogFragment = MyDialogFragment.createInstance("hoge");
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
            }
                break;
            case R.id.button6:
            {
                Intent i = new Intent(getApplicationContext(),MultiFragmentsActivity.class);
                startActivity(i);
            }
                break;
        }
    }

    // コールバックされるメソッド
    // OnOkClickListener インターフェース
    @Override
    public void onOkClicked(Bundle args) {
        if (args != null) {
            String retStr = args.getString(MyDialogFragment.KEY_RET);
            textView.append(retStr);
        }
    }
}
