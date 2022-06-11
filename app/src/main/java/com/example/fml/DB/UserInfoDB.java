package com.example.fml.DB;

import java.util.Date;

import io.realm.*;

public class UserInfoDB extends RealmObject {
    private String email;
    private String gender;
    private String name;
    private String birth;
    private String number;

    public UserInfoDB() {

    }

    public UserInfoDB(String email, String gender, String name, String birth, String number) {
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

