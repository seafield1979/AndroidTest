package com.sunsunsoft.shutaro.testrealm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by shutaro on 2016/10/27.
 */

public class UserMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        // Migrate from version 0 to version 1
        if (oldVersion == 0) {
            // マイグレーション処理
            oldVersion = 1;
        }

        // Migrate from version 1 to version 2
        if (oldVersion == 1) {
            // マイグレーション処理
            oldVersion = 2;
        }
    }
}