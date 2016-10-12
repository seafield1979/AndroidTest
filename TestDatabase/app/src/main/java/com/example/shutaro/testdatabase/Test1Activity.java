package com.example.shutaro.testdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Test1Activity extends AppCompatActivity {

    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.button7)
    Button button7;
    @InjectView(R.id.button8)
    Button button8;
    @InjectView(R.id.button9)
    Button button9;
    @InjectView(R.id.textView)
    TextView textView;

    private static final String TAG = "myLog";
    private Random mRand;

    // @formatter:off
    private String[] NAMES = new String[]{
            "Anastassia", "Juan", "Enrique",
            "Frannie", "Paloma", "Francisco",
            "Lorenzio", "Maryvonne", "Siv",
            "Georgie", "Casimir", "Catharine",
            "Joker"};
    // @formatter:on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.inject(this);

        //Randomクラスのインスタンス化
        mRand = new Random();
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,R.id.button7, R.id.button8, R.id.button9})
    public void onClick(View view) {
        textView.setText("");
        switch (view.getId()) {
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
            case R.id.button7:
                test7();
                break;
            case R.id.button8:
                test8();
                break;
            case R.id.button9:
                test9();
                break;
        }
    }

    /**
     * Contactテーブルを作成
     */
    private void test1() {
        // ContactDbOpenHelperを生成
        ContactDbOpenHelper helper = new ContactDbOpenHelper(this);
        // 書き込み可能なSQLiteDatabaseインスタンスを取得
        SQLiteDatabase db = helper.getWritableDatabase();
        // データベースを閉じる
        db.close();
        helper.close();
    }

    /**
     * データを表示する
     */
    private void test2() {
        testQuery(0);
    }

    /**
     * データを全部表示する
     */
    private void test3() {
        testQuery(1);
    }

    /**
     * データを追加する(リスト)
     */
    private void test4() {
        testQuery(2);
    }

    /**
     * データを追加する(1件)
     */
    private void test5() {
        testQuery(3);
    }

    /**
     * データを更新する
     */
    private void test6() {
        testQuery(4);
    }

    /**
     * データを削除する(一部)
     */
    private void test7() {
        testQuery(5);
    }

    /**
     * データを全部削除する
     */
    private void test8() {
        testQuery(6);
    }

    private void test9() {
        textView.setText("");
    }

    private void testQuery(int mode) {
        ContactDbOpenHelper helper = null;
        SQLiteDatabase db = null;
        try {
            // ContactDbOpenHelperを生成
            helper = new ContactDbOpenHelper(this);
            // 書き込み可能なSQLiteDatabaseインスタンスを取得
            db = helper.getWritableDatabase();

            switch(mode) {
                case 0:
                    searchData(db);
                    break;
                case 1:
                    // データの検索
                    showAllData(db);
                    break;
                case 2:
                    createData(db);
                    break;
                case 3:
                    createData2(db);
                    break;
                case 4:
                    updateData(db);
                    break;
                case 5:
                    deleteData(db);
                    break;
                case 6:
                    deleteAllData(db);
                    break;
            }
        } finally {
            if (db != null) {
                db.close();
            }
            if (helper != null) {
                helper.close();
            }
        }

    }


    private void showAllData(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            // Commentsテーブルのすべてのデータを取得
            cursor = db.query(Contact.TBNAME, null, null,
                    null, null, null,
                    Contact.NAME);
            // Cursorにデータが１件以上ある場合処理を行う
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 名前を取得
                    String name = cursor.getString(cursor
                            .getColumnIndex(Contact.NAME));
                    // 年齢を取得
                    int age = cursor.getInt(cursor.getColumnIndex(Contact.AGE));
                    textView.append(name + ":" + age + "\n");
                }

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void searchData(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            // Commentsテーブルのすべてのデータを取得
//            Cursor query(String table, String[] columns, String selection,
//                    String[] selectionArgs, String groupBy, String having,
//                    String orderBy) {
            cursor = db.query(Contact.TBNAME, null, Contact.AGE + " > ?",
                    new String[]{Integer.toString(10)}, null, null,
                    Contact.NAME);
            // Cursorにデータが１件以上ある場合処理を行う
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 名前を取得
                    String name = cursor.getString(cursor
                            .getColumnIndex(Contact.NAME));
                    // 年齢を取得
                    int age = cursor.getInt(cursor.getColumnIndex(Contact.AGE));
                    textView.append(name + ":" + age + "\n");
                }

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void createData(SQLiteDatabase db) {
        for (int i = 0; i < NAMES.length; i++) {
            // 生成するデータを格納するContentValuesを生成
            ContentValues values = new ContentValues();
            values.put(Contact.NAME, NAMES[i]);
            values.put(Contact.AGE, 10 + i);
            // 戻り値は生成されたデータの_IDが返却される
            long id = db.insert(Contact.TBNAME, null, values);
            textView.append("insert data:" + id + "\n");
        }
    }

    /**
     * 適当なデータを１件追加する
     * @param db
     */
    private void createData2(SQLiteDatabase db) {
        int ran = mRand.nextInt(100);
        // 生成するデータを格納するContentValuesを生成
        ContentValues values = new ContentValues();
        values.put(Contact.NAME, "Shutaro" + ran);
        values.put(Contact.AGE, 10 + ran);
        // 戻り値は生成されたデータの_IDが返却される
        long id = db.insert(Contact.TBNAME, null, values);
        textView.append("insert data:" + id + "\n");

        Cursor cursor = null;
        try {
            // Commentsテーブルのすべてのデータを取得
            cursor = db.query(Contact.TBNAME, null, Contact._ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null,
                    Contact.NAME);
            // Cursorにデータが１件以上ある場合処理を行う
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 名前を取得
                    String name = cursor.getString(cursor.getColumnIndex(Contact.NAME));
                    // 年齢を取得
                    int age = cursor.getInt(cursor.getColumnIndex(Contact.AGE));
                    textView.append(name + ":" + age + "\n");
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 名前にaが含まれる人の年齢をランダムな数に変更
     *
     * @param db
     */
    private void updateData(SQLiteDatabase db) {
        // 更新データを格納するContentValuesを生成
        ContentValues values = new ContentValues();
        // Contact.NAMEにaが含まれるデータの年齢を25に変更
        values.put(Contact.AGE, mRand.nextInt(100));
        // 戻り値は更新した数が返却される
        int n = db.update(Contact.TBNAME, values, Contact.NAME + " like ?",
                new String[]{"%a%"});
        textView.append("update data:" + n + "\n");
    }

    /**
     * 名前がJokerの人を削除
     *
     * @param db
     */
    private void deleteData(SQLiteDatabase db) {
        // Contact.NAMEがJokerのデータを削除
        // 戻り値は削除した数が返却される
        int n = db.delete(Contact.TBNAME, Contact.NAME + " = ?",
                new String[]{"Joker"});
        textView.append("delete data:" + n + "\n");
    }

    /**
     * 全てのデータを削除
     *
     * @param db
     */
    private void deleteAllData(SQLiteDatabase db) {
        // Contact.NAMEがJokerのデータを削除
        // 戻り値は削除した数が返却される
        int n = db.delete(Contact.TBNAME, null, null);
        textView.append("delete data:" + n + "\n");
    }
}
