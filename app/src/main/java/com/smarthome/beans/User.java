package com.smarthome.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class User  extends BeanAbstract{

    private static final long serialVersionUID = -222864131214757024L;
    @Expose
    @DatabaseField(generatedId = true, columnName = "user_id")
    private int id;
    @Expose
    @DatabaseField(columnName = "user_name")
    private  String name;
    @Expose
    @DatabaseField(columnName = "user_password")
    private String password;

    @Expose
    @DatabaseField(columnName = "user_email")
    private String email;
    @Expose
    @DatabaseField(columnName = "user_phone")
    private String phone;
    @Expose
    @DatabaseField(columnName = "user_forname")
    private String forname;

    public User() {
    }

    public User(final String name,final String forname, final String password,final  String email,final String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone=phone;
        this.forname=forname;
    }

    public User(String email,String password) {
        this.password = password;
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Bean getForeignKey(String foreignKey) {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }

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


    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }

    public String getForname() {
        return forname;
    }

    public void setForname(String forname) {
        this.forname = forname;
    }



}
