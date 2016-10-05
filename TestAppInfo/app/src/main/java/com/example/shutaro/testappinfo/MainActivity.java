package com.example.shutaro.testappinfo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private TextView mTextView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test1();
//        test2();
        test3();
    }

    // アプリのバージョンを取得
    private void test1() {
        mTextView = (TextView) findViewById(R.id.text);

        PackageManager pm = getPackageManager();
        int versionCode = 0;
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String msg = String.format("versionCode:%1$s, versionName:%2$s",
                versionCode, versionName);
        mTextView.setText(msg);
    }

    // インストールアプリのリストを表示する
    private void test2() {
        mListView = (ListView) findViewById(R.id.listView);

        ArrayList<String> appList = createAppList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, appList);
        mListView.setAdapter(adapter);
    }

    // インストールアプリのリストを取得する
    private ArrayList<String> createAppList() {
        ArrayList<String> list = new ArrayList<String>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> appInfoList = pm
                .getInstalledApplications(PackageManager.GET_META_DATA);
        for (Iterator<ApplicationInfo> i = appInfoList.iterator(); i.hasNext();) {
            ApplicationInfo appInfo = i.next();

            String appLabel = pm.getApplicationLabel(appInfo).toString();
            list.add(appLabel);
        }

        return list;
    }

    // アクションに対応するアプリの一覧を取得する
    private void test3() {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEARCH);

        List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager
                .MATCH_ALL);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayList<String> nameList = new ArrayList<String>();
        for (ResolveInfo info : activities) {
            nameList.add((String)info.loadLabel(pm));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList);
        mListView.setAdapter(adapter);
    }
}
