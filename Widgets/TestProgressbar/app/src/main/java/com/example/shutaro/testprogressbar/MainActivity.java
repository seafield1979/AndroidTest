package com.example.shutaro.testprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private ProgressBar pb1;
    private ProgressBar pb2;
    private ProgressBar pb3;

    private Button buttonUp;
    private Button buttonDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 関連付け
        pb1 = (ProgressBar)findViewById(R.id.progressBar);
        pb1.setMax(100);
        pb1.setProgress(0);

        pb2 = (ProgressBar)findViewById(R.id.progressBar2);
        pb2.setMax(100);
        pb2.setProgress(0);

        pb3 = (ProgressBar)findViewById(R.id.progressBar3);
        pb3.setMax(100);
        pb3.setProgress(0);

        buttonUp = (Button)findViewById(R.id.button_up);
        buttonUp.setOnClickListener(this);

        buttonDown = (Button)findViewById(R.id.button_down);
        buttonDown.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == buttonUp) {
            int progress = pb2.getProgress();
            pb2.setProgress(progress+10);

        } else if (v == buttonDown) {
            int progress = pb2.getProgress();
            pb2.setProgress(progress-10);
        }
    }
}
