package com.sunsunsoft.shutaro.testrealm;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmMigration;

/**
 * Created by shutaro on 2016/10/27.
 */

public class UserMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // Migrate from version 0 to version 1
        if (oldVersion == 0) {
            // マイグレーション処理
            newVersion = 1;
        }

        // Migrate from version 1 to version 2
        if (oldVersion == 1) {
            // マイグレーション処理
            newVersion = 2;
        }
    }
}