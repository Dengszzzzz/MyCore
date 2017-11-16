package com.dengzh.sample.utils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by dengzh on 2017/11/4.
 */

public class CustomMigrationUtils implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0 && newVersion == 1) {
            RealmObjectSchema personSchema = schema.get("RealmUserBean");
            personSchema.addField("phone",String.class);
            oldVersion++;
        }
    }
}
