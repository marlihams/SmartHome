package com.smarthome.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Mdiallo on 21/12/2015.
 */
public class Device extends BeanAbstract{
    @Expose
    @DatabaseField(generatedId = true, columnName = "device_id")
    private int id;
    @Expose
    @DatabaseField(columnName = "device_name")
    private  String name;
    @Expose
    @DatabaseField(columnName = "device_piece")
    private String pieceName;
    @Expose
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private House house;

    public Device(String name, String pieceName, House house) {
        this.name = name;
        this.pieceName = pieceName;
        this.house = house;
    }
    public Device(House house,String name,String pieceName) {

        this.house = house;
        this.name=name;
        this.pieceName=pieceName;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Device() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public Bean getForeignKey(String foreignKey) {
        return house;
    }


}
