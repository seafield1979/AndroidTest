package com.sunsunsoft.shutaro.testrealm;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Userデータ
 */
public class UserDAO {

    private Realm mRealm;

    public UserDAO(Context context) {
        // Realm.getDefaultInstance() の前に Realm.setDefaultConfiguration をコールしておかないとエラーになる
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .schemaVersion(1)
                .migration(new UserMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Select
     * @return
     */
    public String[] select1(int age) {
        //Realm realm = Realm.getDefaultInstance();

        LinkedList<String> strs = new LinkedList();
        RealmResults<User> results =
                mRealm.where(User.class)
                        .greaterThan("age", age)
                        .findAll();
        for (User user : results) {
            Log.d("select", "name:" + user.getName() + " age:" + user.getAge());
            strs.add(user.getName());
        }

        return null;
    }

    /**
     * 全要素取得
     * @return nameのString[]
     */
    public String[] selectAll() {
        LinkedList<String> strs = new LinkedList();
        RealmResults<User> results = mRealm.where(User.class).findAll();
        for (User user : results) {
            Log.d("select", "id:" + user.getId() + " name:" + user.getName() + " age:" + user
                    .getAge());
            strs.add(user.getName());
        }

        return strs.toArray(new String[0]);
    }

    /**
     * １件選択する
     * @return
     */
    public User selectOne() {
        //Realm realm = Realm.getDefaultInstance();

        User user = mRealm.where(User.class).findFirst();
        if (user != null) {
            Log.d("select", "name:" + user.getName());
        }

        return user;
    }

    /**
     * 要素を追加
     * @param name
     * @param age
     */
    public void add1(String name, int age) {
        //Realm realm = Realm.getDefaultInstance();

        int newId = getNextUserId(mRealm);

        User user = new User();
        user.setId(newId);
        user.setName(name);
        user.setAge(age);

        mRealm.beginTransaction();
        mRealm.copyToRealm(user);
        mRealm.commitTransaction();
    }

    /**
     * 一度にたくさん追加
     */
    public void add2(List<User> list) {
        for (User user : list) {
            int newId = getNextUserId(mRealm);
            user.setId(newId);
            Log.d("userdao", user.getMessage());

            mRealm.beginTransaction();
            mRealm.copyToRealm(user);
            mRealm.commitTransaction();
        }
    }

    /**
     * 1件更新
     * @param id  更新対象のデータのid
     * @param name  更新するname
     * @param age  更新するage
     */
    public void updateOne(int id, String name, int age) {
        mRealm.beginTransaction();
        User user = mRealm.where(User.class).equalTo("id", id).findFirst();

        user.setName(name);
        user.setAge(age);
        mRealm.commitTransaction();
    }

    /**
     * 条件にヒットした全てを更新
     * @param targetAge
     * @param name
     * @param age
     */
    public void updateAll(int targetAge, String name, int age) {
        mRealm.beginTransaction();
        RealmResults<User> results = mRealm.where(User.class).equalTo("age", targetAge).findAll();

        for (int i=0; i<results.size(); i++){
            User user = results.get(i);
            user.setName(name);
            user.setAge(age);
        }
        mRealm.commitTransaction();
    }

    /**
     * 指定のidのデータを削除する
     * @param id
     */
    public void deleteOne(int id) {
        mRealm.beginTransaction();
        RealmResults<User> results = mRealm.where(User.class).equalTo("id", id).findAll();
        results.deleteFirstFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 全件削除する
     */
    public void deleteAll() {
        mRealm.beginTransaction();
        RealmResults<User> results = mRealm.where(User.class).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }


    /**
     * かぶらないプライマリIDを取得する
     * @param realm
     * @return
     */
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
