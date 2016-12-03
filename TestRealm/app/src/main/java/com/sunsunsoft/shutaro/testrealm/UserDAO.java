package com.sunsunsoft.shutaro.testrealm;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Userデータ
 */
public class UserDAO {

    private Realm mRealm;

    public UserDAO(Realm realm) {
        mRealm = realm;
    }

    /**
     * Select
     * @return
     */
    public User selectOne(int age) {
        User result =
                mRealm.where(User.class)
                        .greaterThan("age", age)
                        .isEmpty("name")
                        .contains("name", "hoge")
                        .findFirst();
        return result;
    }

    /**
     * 全要素取得
     * @return nameのString[]
     */
    public List<User> selectAll() {
        RealmResults<User> results = mRealm.where(User.class).findAll();
        for (User user : results) {
            Log.d("select", "id:" + user.getId() + " name:" + user.getName() + " age:" + user
                    .getAge());
        }

        return results;
    }


    /**
     * 条件を直接指定するテスト用 Select
     * @return
     */
    public List<User> selectTest() {
        RealmQuery<User> query = mRealm.where(User.class);

        // 条件1
        query.equalTo("age" , 81);
        query.equalTo("name", "hoge49");

        query.or();

        // 条件2
        query.equalTo("age", 97);
        query.equalTo("name", "hoge97");

        RealmResults<User> results = query.findAll();
        return results;
    }

    /**
     * ソートして全要素追加
     * @return
     */
    public List<User> selectSorted() {
        RealmResults<User> results = mRealm.where(User.class).findAll();
        results = results.sort("age");
        for (User user : results) {
            Log.d("select", "id:" + user.getId() + " name:" + user.getName() + " age:" + user.getAge());
        }

        return results;
    }

    /**
     * １件選択する
     * @return
     */
    public User selectOne() {
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
    public void addOne(String name, int age) {
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
    public void addList(List<User> list) {
        mRealm.beginTransaction();
        for (User user : list) {
            int newId = getNextUserId(mRealm);
            user.setId(newId);
            Log.d("userdao", user.getMessage());

            mRealm.insert(user);
        }
        mRealm.commitTransaction();
    }

    /**
     * データを追加する(insert)
     * 追加したオブジェクトを返さない分、通常のADD(copyToRealm)に比べて高速
     * @param name
     * @param age
     */
    public void insertOne(int pos, String name, int age) {
        int newId = getNextUserId(mRealm);

        User user = new User();
        user.setId(newId);
        user.setName(name);
        user.setAge(age);

        mRealm.beginTransaction();
        mRealm.insert(user);
        mRealm.commitTransaction();
    }

    /**
     * 1件更新
     * @param id  更新対象のデータのid
     * @param newName  更新するname
     * @param newAge  更新するage
     */
    public void updateOne(int id, String newName, int newAge) {
        User user = mRealm.where(User.class).equalTo("id", id).findFirst();

        mRealm.beginTransaction();
        user.setName(newName);
        user.setAge(newAge);
        mRealm.commitTransaction();
    }

    /**
     * 条件にヒットした全てを更新
     * @param targetAge
     * @param name
     * @param age
     */
    public void updateAll(int targetAge, String name, int age) {
        RealmResults<User> results = mRealm.where(User.class).equalTo("age", targetAge).findAll();

        mRealm.beginTransaction();
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
        RealmResults<User> results = mRealm.where(User.class).equalTo("id", id).findAll();

        mRealm.beginTransaction();
        results.deleteFirstFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 全件削除する
     */
    public void deleteAll() {
        RealmResults<User> results = mRealm.where(User.class).findAll();

        mRealm.beginTransaction();
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
