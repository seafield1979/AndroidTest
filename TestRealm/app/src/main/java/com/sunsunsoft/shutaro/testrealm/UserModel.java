package com.sunsunsoft.shutaro.testrealm;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by shutaro on 2016/10/27.
 */

public class UserModel {


    public UserModel(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);

    }

    /**
     * Select
     * @return
     */
    public String[] select1() {
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<User> results = realm.where(User.class).lessThan("age", 2).findAll();


        results.size();

        return null;
    }

    public String[] selectAll() {
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        ArrayList<String> strs = new ArrayList();

        try {
            RealmResults<User> results = realm.where(User.class).findAll();
            for (User user : results) {
                Log.d("select", "name:" + user.getName());
                strs.add(user.getName());
            }

        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return strs.toArray(new String[0]);
    }

    public void add1(String name, int age) {
        Realm realm = Realm.getDefaultInstance();

        int newId = getNextUserId(realm);

        User user = new User();
        user.setId(newId);
        user.setName(name);
        user.setAge(age);

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public void add2() {

    }


    private static RealmConfiguration getConfig(Context context) {
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder(context)
                .schemaVersion(1)
                .migration(new UserMigration())
                .build();
        return defaultConfig;
    }

    public int getNextUserId(Realm realm) {
        // 初期化
        int nextId = 1;
        // userIdの最大値を取得
        Number maxUserId = realm.where(User.class).max("id");
        // 1度もデータが作成されていない場合はNULLが返ってくるため、NULLチェックをする
        if(maxUserId != null) {
            nextId = maxUserId.intValue() + 1;
        }
        return nextId;
    }
}
