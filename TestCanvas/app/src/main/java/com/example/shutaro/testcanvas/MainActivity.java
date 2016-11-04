package com.example.shutaro.testcanvas;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static final int[] buttonIds = {
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button_capture,
                R.id.button_screen
    };

    private SampleView sampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int id : buttonIds) {
            ((Button) findViewById(id)).setOnClickListener(this);
        }

        this.sampleView = (SampleView)findViewById(R.id.custom_view);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.imoni_s);

    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.button1:
                sampleView.setDrawMode(1);
                break;
            case R.id.button2:
                sampleView.setDrawMode(2);
                break;
            case R.id.button3:
                sampleView.setDrawMode(3);
                break;
            case R.id.button4:
                sampleView.setDrawMode(4);
                break;
            case R.id.button5:
                sampleView.setDrawMode(5);
                break;
            case R.id.button6:
                sampleView.setDrawMode(6);
                break;
            case R.id.button_capture:
                // 指定のViewをスクリーンショット
            {
                Bitmap bmp = getViewBitmap(sampleView);
                String imgPath = saveBitmapToSd(bmp);
                toastMake("Save at " + imgPath, 0, 0);
            }
                break;
            case R.id.button_screen:
            {
                Bitmap bmp = getScreenBitmap(sampleView);
                String imgPath = saveBitmapToSd(bmp);
                toastMake("Save at " + imgPath, 0, 0);
            }
                break;
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
