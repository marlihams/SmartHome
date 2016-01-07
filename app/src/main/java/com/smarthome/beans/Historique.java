package com.smarthome.beans;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Mdiallo on 21/12/2015.
 */
public class Historique implements Bean {


    @DatabaseField(generatedId = true, columnName = "historique_id")
    private int id;

    @DatabaseField(columnName = "historique_dateDebut")
    private String dateDebut;

    @DatabaseField(columnName = "historique_dateFin")
    private String dateFin;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private ConsoDevice deviceConso;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private ConsoHouse houseConso;

    public ConsoDevice getDeviceConso() {
        return deviceConso;
    }

    public void setDeviceConso(ConsoDevice deviceConso) {
        this.deviceConso = deviceConso;
    }

    public ConsoHouse getHouseConso() {
        return houseConso;
    }

    public void setHouseConso(ConsoHouse houseConso) {
        this.houseConso = houseConso;
    }

    public Historique(String dateDebut, String dateFin, ConsoDevice deviceConso, ConsoHouse houseConso) {

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.deviceConso = deviceConso;
        this.houseConso = houseConso;
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
        return  foreignKey.equals("consoDevice_id")? deviceConso:houseConso;
    }


    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
