package com.example.shutaro.testasynctask;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6 };

    private TextView mTextView;
    private AsyncTask<Void, Void, String> mTask;
    private AsyncTask<Void, Void, String> mTask2;
    private static final int SLEEP_TIME = 500;  // １回のスリープ時間(ms)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        mTextView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v) {
        switch(v.getId()) {
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

    /**
     * UIスレッドのViewにテキストを表示する
     * @param text
     */
    private void sendText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.append(text);
            }
        });
    }

    private void test1() {
        if (mTask == null) {
            mTask = new AsyncTask<Void, Void, String>() {
                /**
                 * 通信において発生したエラー
                 */
                private Throwable mError = null;

                @Override
                protected String doInBackground(Void... params) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            Thread.sleep(SLEEP_TIME);
                        } catch (InterruptedException e) {
                        }
                        sendText("" + (i + 1) + "\n");
                        Log.d("myLog", "" + (i + 1));
                    }
                    return "finish!!\n";
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // doInBackground前処理
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);

                    // doInBackground後処理
                    mTextView.append(result);
                    mTask = null;
                }
            }.execute();
        } else {
            mTextView.append("task is already running.\n");
        }
    }
    private void test2() {
        if (mTask2 == null) {
            initTask2();
        }

        if (mTask2.getStatus() != AsyncTask.Status.RUNNING) {
            mTask2.execute();
        } else {
            Log.v("myLog", "実行中\n");
        }
    }

    private void test3() {
        new AsyncTask<Void, Void, String>() {
            /**
             * 通信において発生したエラー
             */
            private Throwable mError = null;

            @Override
            protected String doInBackground(Void... params) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                    }
                    sendText("" + (i + 1) + "\n");
                    Log.d("myLog", "" + (i + 1));
                }
                return "finish!!\n";
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // doInBackground前処理
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                // doInBackground後処理
                mTextView.append(result);
                mTask = null;
            }
        }.execute();
    }
    private void test4() {
    }
    private void test5() {
    }
    private void test6() {
        mTextView.setText("");
    }

    private void initTask2() {
        mTask2 = new AsyncTask<Void, Void, String>() {
            /**
             * 通信において発生したエラー
             */
            private Throwable mError = null;

            @Override
            protected String doInBackground(Void... params) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                    }
                    sendText("" + (i + 1) + "\n");
                    Log.d("myLog", "" + (i + 1));
                }
                return "finish!!\n";
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // doInBackground前処理
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                // doInBackground後処理
                mTextView.append(result);
                mTask2 = null;
            }
        };
    }
}