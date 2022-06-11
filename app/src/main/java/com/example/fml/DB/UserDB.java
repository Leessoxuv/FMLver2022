package com.example.fml.DB;

import java.util.Date;

import io.realm.*;

public class UserDB extends RealmObject {
    private String email;
    private String password;

    public UserDB() {

    }
    public UserDB(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
