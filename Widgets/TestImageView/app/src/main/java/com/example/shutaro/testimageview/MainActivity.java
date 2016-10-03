package com.example.shutaro.testimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = (ImageView)findViewById(R.id.imageView3);
        ImageView iv2 = (ImageView)findViewById(R.id.imageView4);
        ImageView iv3 = (ImageView)findViewById(R.id.imageView5);

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), android.R.drawable.btn_star_big_on);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.robot2);

        // bitmapの画像を200x90で作成
        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, 200, 90, false);
        iv.setImageBitmap(bmp2);

        // bitmapの画像を200x200で作成
        Bitmap bmp3 = Bitmap.createScaledBitmap(bmp, 200, 200, false);
        iv2.setImageBitmap(bmp3);

        // bitmapの画像を200x200で作成
        Bitmap bmp4 = Bitmap.createScaledBitmap(bmp, 100, 200, false);
        iv3.setImageBitmap(bmp4);
    }
}
