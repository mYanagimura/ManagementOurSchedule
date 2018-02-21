package com.example.kukilemo.managementourschedule;

import android.app.Application;
import android.util.Log;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static android.content.ContentValues.TAG;

/**
 * Created by kukilemo on 18/01/28.
 */

public class OurSchedulerApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig=new RealmConfiguration.Builder().build();
        // Realm.deleteRealm(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);

        //今後はリモート端末とリアルタイム同期を想定しています
//        SyncCredentials credentials=SyncCredentials.usernamePassword(
//                "test@example.com",
//                "test", false
//        );
//        SyncUser.loginAsync(credentials,
//                "http://52.90.44.187:9080",
//                new SyncUser.Callback() {
//                    @Override
//                    public void onSuccess(SyncUser user) {
//                        RealmConfiguration config =
//                                new SyncConfiguration.Builder(
//                                        user,
//                                        "realm://52.90.44.187:9080/~/debug").build();
//                        Realm.setDefaultConfiguration(config);
//                    }
//
//                    @Override
//                    public void onError(ObjectServerError error) {
//                        Log.e(TAG, "onError: ", error);
//                    }
//                });
//
//
    }
}
