package com.example.shutaro.testwebview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class Test1Activity extends AppCompatActivity implements OnClickListener {

    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6 };
    WebView mWebView;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }
        mWebView = (WebView)findViewById(R.id.webView);
        mProgress = (ProgressBar)findViewById(R.id.progressBar);


        // 進捗状況を取得
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgress.setProgress(newProgress);
            }
        });
    }

    // 端末のバックキーを押した時の処理
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
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
     * Webページを表示する
     */
    private void test1() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://www.sunsunsoft.com");
    }

    /**
     * ローカルファイルを表示する
     */
    private void test2() {
        mWebView.loadUrl("file:///android_asset/hoge.html");
    }

    /**
     * UserAgentを変更する
     */
    private void test3() {
        String ua = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.63 Safari/537.36";
        mWebView.getSettings().setUserAgentString(ua);
    }

    /**
     * ページを再読み込みする
     */
    private void test4() {
        mWebView.reload();
    }

    /**
     * 前のページに戻る
     */
    private void test5() {
        mWebView.goBack();
    }

    /**
     * 元のページに進む（戻った後のページで実行)
     */
    private void test6() {
        mWebView.goForward();
    }
}