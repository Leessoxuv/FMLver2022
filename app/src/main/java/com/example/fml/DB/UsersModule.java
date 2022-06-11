package com.example.fml.DB;

import io.realm.*;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {UserDB.class, UserInfoDB.class})
public class UsersModule {
}
