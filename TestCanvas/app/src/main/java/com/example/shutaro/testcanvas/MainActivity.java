package com.example.shutaro.testcanvas;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button_capture;
    private SampleView sampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        this.button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        this.button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);
        this.button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        this.button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        this.button_capture = (Button)findViewById(R.id.button_capture);
        button_capture.setOnClickListener(this);

        this.sampleView = (SampleView)findViewById(R.id.custom_view);
    }

    public void onClick(View v){
        if (v == button1) {
            sampleView.setDrawMode(1);
        } else if (v == button2) {
            sampleView.setDrawMode(2);
        } else if (v == button3) {
            sampleView.setDrawMode(3);
        } else if (v == button4) {
            sampleView.setDrawMode(4);
        } else if (v == button5) {
            sampleView.setDrawMode(5);
        } else if (v == button_capture) {
            // 指定のViewをスクリーンショット
            Bitmap bmp = getViewBitmap(sampleView);
            String imgPath = saveBitmapToSd(bmp);
            toastMake("Save at " + imgPath, 0, 0);
        }
    }

    // Toast を表示する
    private void toastMake(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, x, y);
        toast.show();
    }

    /**
     * スクリーンショットしたBitmapを作成する
     * @param view このviewの親Viewのスクリーンショットを撮る
     * @return Bitmap
     */
    public Bitmap getScreenBitmap(View view){
        return getViewBitmap(view.getRootView());
    }

    /**
     * 指定したViewのBitmapを作成する
     * @param view
     * @return Bitmap
     */
    public Bitmap getViewBitmap(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap cache = view.getDrawingCache();
        if(cache == null){
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cache);
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 外部ストレージのtestフォルダにBitmapを保存する
     * /storage/emulated/0/test
     * @param mBitmap
     * @return String 保存ファイルのパス
     */
    public String saveBitmapToSd(Bitmap mBitmap) {
        try {
            // sdcardフォルダを指定
            String saveDir = Environment.getExternalStorageDirectory().getPath() + "/test";
            // SD カードフォルダを取得
            File file = new File(saveDir);

            // フォルダ作成
            if (!file.exists()) {
                if (!file.mkdir()) {
                    Log.e("Debug", "Make Dir Error");
                }
            }

            // 日付でファイル名を作成　
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String imgPath = saveDir + "/" + fileName.format(mDate) + ".jpg";

            // ファイル保存
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(imgPath, true);
//                fos.write(data);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                // アンドロイドのデータベースへ登録
                // (登録しないとギャラリーなどにすぐに反映されないため)
                registAndroidDB(imgPath);

            } catch (Exception e) {
                Log.e("Debug", e.getMessage());
            }

            fos = null;
            return imgPath;
        } catch (Exception e) {
            Log.e("Error", "" + e.toString());
        }
        return null;
    }

    /**
     * アンドロイドのデータベースへ画像のパスを登録
     * @param path 登録するパス
     */
    private void registAndroidDB(String path) {
        // アンドロイドのデータベースへ登録
        // (登録しないとギャラリーなどにすぐに反映されないため)
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = MainActivity.this.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
