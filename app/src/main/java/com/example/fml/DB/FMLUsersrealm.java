package com.example.fml.DB;

import io.realm.RealmObject;

public class FMLUsersrealm extends RealmObject {
    private String email;
    private String password;
    private String gender;
    private String name;
    private String birth;
    private String number;

    public FMLUsersrealm() {

    }
    public FMLUsersrealm(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public FMLUsersrealm(String email, String gender, String name, String birth, String number) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

