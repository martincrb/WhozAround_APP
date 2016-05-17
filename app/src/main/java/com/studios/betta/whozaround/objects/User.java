package com.studios.betta.whozaround.objects;

import java.util.ArrayList;

/**
 * Created by Martin on 16/04/2016.
 */
public class User {
    public Long _id; // For cupboard model
    public String name;
    public String surname;
    public String facebook_username;
    public String gcmToken;
    public String hometown;
    public String email;
    public String gender;
    public Integer age;
    public ArrayList<String> friends;

    public User(String facebook_username, String name, String surname, String hometown, String email, String gender, String age, String gcm_token) {
        this.name = name;
        this.surname = surname;
        this.facebook_username = facebook_username;
        this.hometown = hometown;
        this.email = email;
        this.gcmToken = gcm_token;
        this.gender = gender;
        this.age = Integer.parseInt(age);
    }
}
