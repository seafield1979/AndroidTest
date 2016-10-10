package com.example.shutaro.testhttp;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6 };

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }
        mTextView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v) {
        mTextView.setText("");

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

    private void testHttpConnect(int mode) {
        new AsyncTask<Integer, Void, String>() {
            /**
             * 通信において発生したエラー
             */
            private Throwable mError = null;

            @Override
            protected String doInBackground(Integer... params) {
                httpConnectMain(params[0]);
                if (isCancelled()) {
                    sendText("\n通信はキャンセル済みでした。");
                }
                return null;
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
            }
        }.execute(mode);
    }

    private void httpConnectMain(int mode) {
        switch (mode) {
            case 0:
                // hello world
            {
                URL url = null;
                try {
                    url = new URL("http://sunsunsoft.com/test/android/hello.php");
                } catch (Exception e) {
                    return;
                }

                httpConnectGet(url);
            }
                break;
            case 1:
                // getパラメータ
            {
                URL url = null;
                try {
                    url = new URL("http://sunsunsoft.com/test/android/test_get" +
                            ".php?param1=value1&param2=value2");
                } catch (Exception e) {
                    return;
                }

                httpConnectGet(url);
            }
                break;
            case 2:
                // post パラメータ
            {
                URL url = null;
                try {
                    url = new URL("http://sunsunsoft.com/test/android/test_post.php");
                } catch (MalformedURLException e) {
                    return;
                }

                httpConnectPost(url, "param1=value1&param2=value2");
            }
                break;
            case 3:
            {
                mTextView.append("--json--\n");

                // POSTでjson送信
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("param1", "value1");
                    jsonObject.put("param2", "value2");
                } catch (Exception e) {
                    Log.e("myLog", e.toString());
                }

                httpConnectPostJson( jsonObject);
            }
                break;

        }
    }

    /**
     * HttpURLConnection を使ってhttp通信
     * GETメッセージ送信
     */
    private void test1() {
        testHttpConnect(0);
    }

    /**
     * POSTメッセージ送信
     */
    private void test2() {
        testHttpConnect(1);
    }

    /**
     * POSTメッセージ(JSON)送信
     */
    private void test3() {
        testHttpConnect(2);
    }
    private void test4() {
        testHttpConnect(3);
    }

    private void test5() {
        mTextView.setText("");
    }

    private void test6() {
        mTextView.setText("");
    }

    private void sendText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.append(text);
            }
        });
    }

    /**
     * HttpURLConnection を使用したHttp通信処理
     * @param url
     */
    private void httpConnectGet(URL url) {
        HttpURLConnection con = null;

        // URLの作成
        try {
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // ヘッダーの設定(複数設定可能)
            con.setRequestProperty("Accept-Language", "jp");

            // 接続
            con.connect();

            // ヘッダの取得
            Map headers = con.getHeaderFields();
            Iterator headerIt = headers.keySet().iterator();
            String header = null;
            while(headerIt.hasNext()){
                String headerKey = (String)headerIt.next();
                header += headerKey + "：" + headers.get(headerKey) + "\r\n";
            }
            if (header != null) {
                sendText(header);
            }

            // 本文の取得
            sendText("-- body --\n");
            InputStreamReader ir = new InputStreamReader(
                    con.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(ir);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            sendText(sb.toString());
        } catch (Exception e) {
            sendText(e.toString());
        }
    }

    /**
     * HttpURLConnection を使用したHttp通信処理
     * @param url
     */
    private void httpConnectPost(URL url, String postStr) {
        HttpURLConnection con = null;

        // URLの作成
        try {
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // ヘッダーの設定(複数設定可能)
            con.setRequestProperty("Accept-Language", "jp");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
            // 接続
            con.connect();

            // データを送信する
            OutputStreamWriter out = new   OutputStreamWriter(con.getOutputStream());
            out.write(postStr);
            out.close();

            // ヘッダの取得
            Map headers = con.getHeaderFields();
            Iterator headerIt = headers.keySet().iterator();
            String header = null;
            while(headerIt.hasNext()){
                String headerKey = (String)headerIt.next();
                header += headerKey + "：" + headers.get(headerKey) + "\r\n";
            }
            if (header != null) {
                sendText(header);
            }

            // 本文の取得
            sendText("-- body --\n");
            InputStreamReader ir = new InputStreamReader(
                    con.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(ir);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            sendText(sb.toString());
        } catch (Exception e) {
            sendText(e.toString());
        }
    }

    /**
     * PostでJsonデータを送信
     * @param json
     */
    private void httpConnectPostJson(JSONObject json) {
        HttpURLConnection con = null;


        // URLの作成
        try {
            URL url = new URL("http://sunsunsoft.com/test/android/test_json.php");

            sendText(json.toString());

            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection) url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // ヘッダーの設定(複数設定可能)
            con.setRequestProperty("Accept-Language", "jp");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // 接続
            con.connect();

            // データを送信する
            OutputStreamWriter out = new   OutputStreamWriter(con.getOutputStream());
            out.write(json.toString());
            out.close();

            // ヘッダの取得
            Map headers = con.getHeaderFields();
            Iterator headerIt = headers.keySet().iterator();
            String header = null;
            while (headerIt.hasNext()) {
                String headerKey = (String) headerIt.next();
                header += headerKey + "：" + headers.get(headerKey) + "\r\n";
            }
            if (header != null) {
                sendText(header);
            }

            // 本文の取得
            sendText("-- body --\n");
            InputStreamReader ir = new InputStreamReader(
                    con.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(ir);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            sendText(sb.toString());
        } catch (Exception e) {
            sendText(e.toString());
        }
    }
}

