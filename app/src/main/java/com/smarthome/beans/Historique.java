package com.smarthome.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mdiallo on 21/12/2015.
 */
@DatabaseTable(tableName = "Historique")
public class Historique extends BeanAbstract {

    @Expose
    @DatabaseField(generatedId = true)
    private int id;
    @Expose
    @DatabaseField
    private String periode;

    @Expose
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Device device;

    @Expose
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private House house;

    @Expose
    @DatabaseField
    private double consommation;

    public Historique(String periode, Device device, double consommation) {
        this.periode = periode;
        this.device = device;
        this.consommation = consommation;
    }

    public Historique(String periode, House house, double consommation) {
        this.periode = periode;
        this.house = house;
        this.consommation = consommation;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public Historique() {

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
        return foreignKey.equals("device_id")?device:house;
    }
}
