package com.sunsunsoft.shutaro.testrealm;

/**
 * Created by shutaro on 2016/12/03.
 */


import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Realmのオブジェクトを管理する
 */

public class RealmManager {
    public static Realm realm;
    public static final int Version1 = 1;
    public static final int latestVersion = Version1;


    public static Realm getRealm() { return realm; }

    private static UserDAO userDAO;
    private static boolean initFlag;

    public static void initRealm(Context context) {
        if (initFlag) return;
        initFlag = true;

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .schemaVersion(latestVersion)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        userDAO = new UserDAO(realm);
    }

    public static UserDAO getUserDao(){
        return userDAO;
    }

    public static void closeRealm() {
        realm.close();
    }
}
