package com.sunsunsoft.shutaro.testxmlparser;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
implements OnClickListener{
    /**
     * enum
     */
    // ファイルの保存先の種類
    enum DirType{
        AppStorage,     // アプリの永続化ストレージ
        AppCache,       // アプリのキャッシュ（一時的に使用する）領域
        AppExternal,    // アプリの外部
        ExternalStorage,        // 外部ストレージ
        ExternalDocument,       // 外部ストレージ(共有ドキュメント)
        ExternalDownload,       // 外部ストレージ(共有ダウンロード)
    }

    /**
     * Consts
     */
    public static final String XmlFileName1 = "test1.xml";
    public static final String XmlFileName2 = "test2.xml";
    public static final String XmlFileName3 = "test3.xml";
    public static final String XmlFileName4 = "test4.xml";

    private int[] button_ids = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };
    private Button[] buttons = new Button[button_ids.length];
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<buttons.length; i++) {
            buttons[i] = (Button)findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        textView = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                readResourceXml();
                break;
            case R.id.button2:
                writeCardXml();
                break;
            case R.id.button3:
                readCardXml();
                break;
            case R.id.button4:
                writeBookXml();
                break;
            case R.id.button5:
                readBookXml();
                break;
            case R.id.button6:
                writeTest1Xml();
                break;
            case R.id.button7:
                readTest1Xml();
                break;
            case R.id.button8:
                writeTest2Xml();
                break;
            case R.id.button9:
                readTest2Xml();
                break;

        }
    }

    /**
     * res/raw 以下のxmlファイルを読み込む
     */
    private void readResourceXml() {
        // 読み込む先は XmlTangoBook
        XmlTangoBook book = UXmlParser.readTangoBook(this, R.raw.book2);
        textView.setText(book.toString());
    }

    /**
     * ストレージにxmlファイルを書き出す
     */
    private void writeCardXml() {
        XmlTangoCard card = new XmlTangoCard();
        card.setWordA("apple");
        card.setWordB("りんご");
        card.setComment("PPAP");

        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File result = new File(path, XmlFileName1);
            serializer.write(card, result);
        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
        textView.setText("ok");
    }

    /**
     * ストレージからxmlファイルを読みこむ
     */
    private void readCardXml() {
        // XmlTangoCardを読み込む
        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File source = new File(path, XmlFileName1);
            XmlTangoCard card = serializer.read(XmlTangoCard.class, source);

            textView.setText(card.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ストレージのxmlファイルにBook情報をを書き込む
     */
    private void writeBookXml() {
        XmlTangoBook book = new XmlTangoBook();
        book.setName("book1");
        book.setComment("this is a book");

        LinkedList<XmlTangoCard> cards = new LinkedList<>();
        XmlTangoCard card = new XmlTangoCard();
        card.setWordA("apple");
        card.setWordB("りんご");
        card.setComment("1");
        cards.add(card);

        card = new XmlTangoCard();
        card.setWordA("orange");
        card.setWordB("オレンジ");
        card.setComment("2");
        cards.add(card);

        book.setCards(cards);

        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File result = new File(path, XmlFileName2);
            serializer.write(book, result);
        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
        textView.setText("ok");
    }

    /**
     * XmlファイルからXmlTangoBookを読み込む
     */
    private void readBookXml() {
        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File source = new File(path, XmlFileName2);
            XmlTangoBook book = serializer.read(XmlTangoBook.class, source);

            textView.setText(book.toString());

        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
    }


    /**
     * ファイルにTest1のxmlを書き込む
     */
    private void writeTest1Xml() {

        XmlTest1 data = new XmlTest1();
        data.setName("apple");

        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File result = new File(path, XmlFileName3);
            serializer.write(data, result);
        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
        textView.setText("ok");
    }

    /**
     * xmlファイルからTest1を読み込む
     */
    private void readTest1Xml() {
        try {
            Serializer serializer = new Persister();
            File path = getPath(DirType.AppExternal);
            File source = new File(path, XmlFileName3);
            XmlTest1 data = serializer.read(XmlTest1.class, source);

            textView.setText(data.toString());

        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
    }

    /**
     * ファイルにTest1のxmlを書き込む
     */
    private void writeTest2Xml() {

        XmlTest12 data = new XmlTest12();
        data.setName("apple");

        LinkedList<Test1> list = new LinkedList<>();

        Test1 test = new Test1();
        test.setName("hoge");
        list.add(test);

        test = new Test1();
        test.setName("hoge2");
        list.add(test);

        data.setList(list);

        File path = getPath(DirType.ExternalDocument);
        File result = new File(path, XmlFileName4);
        try {
            Serializer serializer = new Persister();
            serializer.write(data, result);
        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
        textView.setText("write to " + result.toString());
    }

    /**
     * xmlファイルからTest12を読み込む
     */
    private void readTest2Xml() {
        File path = getPath(DirType.ExternalDocument);
        File source = new File(path, XmlFileName4);

        try {
            Serializer serializer = new Persister();
            XmlTest12 data = serializer.read(XmlTest12.class, source);

            textView.setText(data.toString());

        } catch (Exception e) {
            Log.e("tag", e.toString());
            textView.setText("failed");
            return;
        }
        textView.append("\n" + source.toString());
    }

    private File getPath(DirType dirType) {
        switch (dirType) {
            case AppStorage:
                return getFilesDir();
            case AppCache:
                return getCacheDir();
            case AppExternal:
            {
                File[] dirs = getExternalFilesDirs(null);
                StringBuffer buf = new StringBuffer();
                if (dirs != null && dirs.length > 0) {
                    return dirs[0];
                }
            }
            case ExternalStorage:
                return Environment.getExternalStorageDirectory();
            case ExternalDocument:
                return Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOCUMENTS);
            case ExternalDownload:
                return Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS);
        }
        return null;
    }
}
