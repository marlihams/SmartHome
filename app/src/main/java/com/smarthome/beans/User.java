package com.smarthome.beans;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class User {
   private  String name;
    private String password;
    private String address;
    private String email;
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User(String email,String password) {
        this.password = password;
        this.email = email;
    }

}
