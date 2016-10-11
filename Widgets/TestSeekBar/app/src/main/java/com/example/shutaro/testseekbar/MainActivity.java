package com.example.shutaro.testseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
    @InjectView(R.id.seekBar)
    SeekBar seekBar;
    @InjectView(R.id.positionText)
    TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            // トラッキング開始時に呼び出されます
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                positionText.setText(String.valueOf(seekBar.getProgress()));
                textView.append("onStartTrackingTouch\n");
            }
            // トラッキング中に呼び出されます
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                positionText.setText(String.valueOf(progress) + ", " + String.valueOf(fromTouch));
                textView.append("onProgressChanged\n");
            }
            // トラッキング終了時に呼び出されます
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                positionText.setText(String.valueOf(seekBar.getProgress()));
                textView.append("onStopTrackingTouch\n");
            }
        });
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                test1();
                break;
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    private void test1() {
        seekBar.incrementProgressBy(10);
    }

    private void test2() {
        seekBar.incrementProgressBy(-10);
    }

    private void test3() {
        seekBar.setProgress(0);
    }

    private void test4() {

    }

    private void test5() {

    }

    private void test6() {
        textView.setText("");
    }
}
