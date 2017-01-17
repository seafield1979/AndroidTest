package com.sunsunsoft.shutaro.testxmlparser;

import java.io.InputStream;

import android.content.Context;
import android.util.Log;

import org.simpleframework.xml.core.Persister;

/**
 * Created by shutaro on 2016/12/17.
 */

public class UXmlParser {
    /**
     * Constants
     */
    public static final String TAG = "UXmlParser";


    /**
     * 単語帳のxmlを読み込む
     * @param context
     * @return
     */
    static XmlTangoBook readTangoBook(Context context, int xmlId) {
        // res/book.xmlを生ファイルとして開く
        InputStream is = context.getResources().openRawResource(xmlId);
        if (is == null) return null;

        Persister persister = new Persister();
        XmlTangoBook book = null;
        try {
            // 読み込む
            book = persister.read(XmlTangoBook.class, is);
        } catch (Exception e) {
            Log.d("error:", e.toString());
            return null;
        }


        if(book != null) {
            Log.d(TAG, book.getName() + " " + book.getComment());

            for (XmlTangoCard card : book.getCards()) {
                Log.d(TAG, card.getWordA() + " " + card.getWordB() + " " +
                        card.getComment());
            }
        }
        return book;
    }
}
