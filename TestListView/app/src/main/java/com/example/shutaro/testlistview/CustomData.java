package com.example.shutaro.testlistview;


import android.graphics.Bitmap;

/**
 * Created by shutaro on 2016/10/03.
 */
public class CustomData {
    private Bitmap mImageData;
    private String mTextData;
    private int id;

    private static int count = 0;

    public CustomData() {
        mImageData = null;
        mTextData = null;
        id = CustomData.count;
        CustomData.count++;
    }

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

    public int getId() {
        return this.id;
    }
}
