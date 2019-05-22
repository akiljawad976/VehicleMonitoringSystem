package com.example.mist.vehiclemonitoringsystem;

/**
 * Created by Akil on 8/21/2017.
 */

public class User {
    public String email;
    public String password;
    public String phonenum;
    public String id;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getId() {
        return id;
    }

    public User() {

    }

    public User(String email, String password, String phonenum, String id) {
        this.email = email;
        this.password = password;
        this.phonenum = phonenum;
        this.id = id;
    }
}
