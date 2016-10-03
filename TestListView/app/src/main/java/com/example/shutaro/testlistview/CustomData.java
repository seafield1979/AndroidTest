package com.example.shutaro.testlistview;


import android.graphics.Bitmap;

/**
 * Created by shutaro on 2016/10/03.
 */
public class CustomData {
    private Bitmap mImageData;
    private String mTextData;

    public void setImagaData(Bitmap image) {
        mImageData = image;
    }

    public Bitmap getImageData() {
        return mImageData;
    }

    public void setTextData(String text) {
        mTextData = text;
    }

    public String getTextData() {
        return mTextData;
    }
}
