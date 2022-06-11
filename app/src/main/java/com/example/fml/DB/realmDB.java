package com.example.fml.DB;

import android.app.*;
import io.realm.*;

public class realmDB extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("FMLUsers.realm") //생성할 realm파일 이름 지정
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
