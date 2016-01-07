package com.smarthome.beans;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Mdiallo on 21/12/2015.
 */
public class ConsoHouse implements Bean {


    @DatabaseField(generatedId = true, columnName = "consoHouse_id")
    private int id;

    @DatabaseField(columnName = "consoHouse_consommation")
    private  String consomation;

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

