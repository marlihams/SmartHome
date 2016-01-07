package com.smarthome.beans;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Mdiallo on 21/12/2015.
 */
public class  ConsoDevice implements Bean {


    @DatabaseField(generatedId = true, columnName = "consoDevice_id")
    private int id;

    @DatabaseField(columnName = "consoDevice_consommation")
    private  String consomation;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private  Device device;

    public ConsoDevice(String consomation, Device device) {
        this.consomation = consomation;
        this.device = device;
    }
    public ConsoDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public ConsoDevice() {
    }

    public ConsoDevice(String consomation) {

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
        return device;
    }


}
