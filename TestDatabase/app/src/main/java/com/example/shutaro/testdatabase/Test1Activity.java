package com.example.shutaro.testdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import android.widget.AdapterView.OnItemClickListener;

enum TestMode {
    Select,
    SelectAll,
    Add1,
    Add2,
    Update,
    Delete,
    DeleteAll,
    Clear
};

public class Test1Activity extends AppCompatActivity implements OnItemClickListener {

    @InjectView(R.id.button)
    Button button;

    public static final String[] modeItems = new String[]{
            "Select",
            "Select All",
            "Add1",
            "Add2",
            "Update",
            "Delete",
            "DeleteAll",
            "Clear"
    };
    private ListView listView;

    TextView textView;

    private static final String TAG = "myLog";
    private Random mRand;

    // @formatter:off
    private final String[] NAMES = new String[]{
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

        textView = (TextView)findViewById(R.id.textView);

        //Randomクラスのインスタンス化
        mRand = new Random();

        listView = (ListView)findViewById(R.id.listView);

        // Adapterの作成
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeItems);
        // Adapterの設定
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        test1();
    }

    // イベント処理をまとめる
    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                textView.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = (ListView) parent;
        Log.v("myLog", (String) lv.getItemAtPosition(position));

        TestMode[] values = TestMode.values();
        TestMode mode = values[position];

        testQuery(mode);
        switch (mode) {
            case Select:
            case SelectAll:
            case Add1:
            case Add2:
            case Update:
            case Delete:
            case DeleteAll:
                testQuery(mode);
                break;
            case Clear:
                textView.setText("");
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

    private void testQuery(TestMode mode) {
        ContactDbOpenHelper helper = null;
        SQLiteDatabase db = null;
        try {
            // ContactDbOpenHelperを生成
            helper = new ContactDbOpenHelper(this);
            // 書き込み可能なSQLiteDatabaseインスタンスを取得
            db = helper.getWritableDatabase();

            switch(mode) {
                case Select:
                    select2(db);
                    break;
                case SelectAll:
                    selectAll(db);
                    break;
                case Add1:
                    createData(db);
                    break;
                case Add2:
                    createData2(db);
                    break;
                case Update:
                    updateData2(db);
                    break;
                case Delete:
                    deleteData(db);
                    break;
                case DeleteAll:
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

    /**
     * 全件取得
     * @param db
     */
    private void selectAll(SQLiteDatabase db) {
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

    /**
     * SELECT文
     * @param db
     */
    private void select1(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            // Commentsテーブルのすべてのデータを取得
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

    /**
     * SELECTのrawQuery版
     * @param db
     */
    private void select2(SQLiteDatabase db) {
        String query = "SELECT * FROM " + Contact.TBNAME + " WHERE " + Contact.AGE + " > 30";
        Cursor cursor = db.rawQuery(query, null);

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

        cursor.close();
    }

        /**
         * NAMESのリストをまとめて追加
         * @param db
         */
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
        values.put(Contact.AGE, 100);
        // 戻り値は更新した数が返却される
        int n = db.update(Contact.TBNAME, values, Contact.AGE + " = 11",
                null);
        textView.append("update data:" + n + "\n");
    }


    /**
     * rawQueryを使用したupdate
     * @param db
     */
    private void updateData2(SQLiteDatabase db) {
        String query = "UPDATE t_contact SET f_age=101 WHERE f_age=100";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
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
