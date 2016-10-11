package com.example.shutaro.testwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TestCookieActivity extends AppCompatActivity implements OnClickListener {
    private Button[] buttons = new Button[6];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6 };
    WebView mWebView;
    TextView mTextView;
    private static final String domainName = "http://www.sunsunsoft.com";
    private static final String domainName2 = "http://www.yahoo.co.jp";
    private String mDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cookie);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }
        mTextView = (TextView)findViewById(R.id.textView);

        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
             @Override
             public void onPageFinished(WebView view, String url){
                 // Cookieを取得
                 showCookies(url);
             }
         });
        mDomain = domainName;
        mWebView.loadUrl("http://www.sunsunsoft.com/test/android/show_cookie.php");
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
        showCookies(mDomain);
    }

    /**
     * ローカルファイルを表示する
     */
    private void test2() {
        setCookies(mDomain, "android1=100;android2=200;");
    }
    private void test3() {
        removeCookies();
    }
    private void test4() {
        mWebView.reload();
    }
    private void test5() {
        mDomain = domainName2;
        mWebView.loadUrl(domainName2);
    }
    private void test6() {
        mTextView.setText("");
    }

    /**
     * Cookieを取得する
     * @param url
     */
    private void showCookies(String url) {
        CookieManager cm = CookieManager.getInstance();
        String cookieStr = cm.getCookie(url);
        if (cookieStr != null) {
            String[] cookies = cookieStr.split(";");
            for (String cookie : cookies) {
                mTextView.append(cookie + "\n");
            }
        }
    }

    /**
     * Cookieを設定する
     * @param url
     * @param cookies  "param1=value2;param2=value2;"
     */
    private void setCookies(String url, String cookies) {
        CookieManager cm = CookieManager.getInstance();
        for (String cookie : cookies.split(";")) {
            cm.setCookie(url, cookie);
        }
        cm.flush();

        mTextView.append("Add cookies " + cookies + "\n");
    }

    /**
     * Cookieを削除する
     */
    private void removeCookies() {
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                mTextView.append("onReceiveValue " + value + "\n");
            }
        });
    }
}