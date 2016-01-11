package com.smarthome.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mdiallo on 20/12/2015.
 */
@DatabaseTable(tableName = "house")
public class House extends BeanAbstract{

    @Expose
    @DatabaseField(generatedId = true, columnName = "house_id")
    private int id;
    @Expose
    @DatabaseField(columnName = "house_name")
    private  String name;
    @Expose
    @DatabaseField(columnName = "house_address")
    private String address;
    @Expose
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private User user;

    public House() {
        super();
    }

    public House(String name, String address, User user) {
        this.name = name;
        this.address = address;
        this.user = user;
    }
    public House( User user,String name) {
        this.user = user;
        this.name=name;
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
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
