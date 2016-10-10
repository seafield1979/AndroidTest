package com.example.shutaro.testhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ImageDownloadActivity extends AppCompatActivity implements OnClickListener {

    private Button[] buttons = new Button[3];
    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3};

    private EditText mEdit;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        mEdit = (EditText)findViewById(R.id.editText);
        mImageView = (ImageView)findViewById(R.id.imageView);

        mEdit.setText("http://sunsunsoft.com/image/miro2_s.jpg");
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
        }
    }

    private void sendBitmap(final Bitmap bmp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mImageView.setImageBitmap(bmp);
            }
        });
    }

    private void test1() {
        String imageUrl = mEdit.getText().toString();

        new AsyncTask<String, Void, String>() {
            /**
             * 通信において発生したエラー
             */
            private Throwable mError = null;

            @Override
            protected String doInBackground(String... params) {
                Bitmap bmp = downloadImage(params[0]);
                if (bmp != null) {
                    sendBitmap(bmp);
                }
                if (isCancelled()) {
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
        }.execute(imageUrl);
    }
    private void test2() {
    }
    private void test3() {
    }

    private Bitmap downloadImage(String imageUrl) {
        // 受け取ったbuilderでインターネット通信する
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try{

            URL url = new URL(imageUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (MalformedURLException exception){

        }catch (IOException exception){

        }finally {
            if (connection != null){
                connection.disconnect();
            }
            try{
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (IOException exception){
            }
        }

        return bitmap;
    }
}
