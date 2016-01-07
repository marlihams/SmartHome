package com.smarthome.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Mdiallo on 21/12/2015.
 */
public class ConsoHouse extends BeanAbstract {

    @Expose
    @DatabaseField(generatedId = true, columnName = "consoHouse_id")
    private int id;
    @Expose
    @DatabaseField(columnName = "consoHouse_consommation")
    private  String consomation;
    @Expose
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private  House house;

    public ConsoHouse(String consomation, House house) {
        this.consomation = consomation;
        this.house = house;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public ConsoHouse() {
    }

    public ConsoHouse(String consomation) {

        this.consomation = consomation;

    }

    public String getConsomation() {
        return consomation;
    }

    public void setConsomation(String consomation) {
        this.consomation = consomation;
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

