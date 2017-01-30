package com.example.shutaro.testlistview;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * ListViewに表示する項目をカスタマイズするためのクラス
 * getViewがカスタマイズされたViewを返す
 */
public class CustomAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater mLayoutInflater;

    public CustomAdapter(Context context, int textViewResourceId,
                         List<CustomData> objects)
    {
        super(context, textViewResourceId, objects);
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomData item = getItem(position);

        if (position % 10 == 0) {
            convertView = mLayoutInflater.inflate(
                    R.layout.custom_list_item_header, null);

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText("hogehogehgoehoge");
            convertView.setBackgroundColor(Color.rgb(200, 200, 0));
        } else {
            convertView = mLayoutInflater.inflate(
                    R.layout.custom_list_item, null);
            Log.v("myData", String.valueOf(position));

            ImageView imageView;
            imageView = (ImageView) convertView.findViewById(R.id.image);
            imageView.setImageBitmap(item.getImageData());

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(item.getTextData());

            if (position % 2 == 1) {
                convertView.setBackgroundColor(Color.rgb(200, 100, 0));
            } else {
                convertView.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }
        return convertView;
    }
}
