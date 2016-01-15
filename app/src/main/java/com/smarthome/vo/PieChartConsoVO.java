package com.smarthome.vo;

/**
 * Created by Mdiallo on 15/01/2016.
 */
public class PieChartConsoVO {
    private Double consommation;
    private String name;
    private int color;
    private String mmYear;

    public Double getConsommation() {
        return consommation;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public String getMmYear() {
        return mmYear;
    }

    public void setMmYear(String mmYear) {
        this.mmYear = mmYear;
    }

    public PieChartConsoVO(String mmYear,String name,double conso,int color ){
        this.name=name;
        consommation=conso;
        this.color=color;

        this.mmYear=mmYear;
    }

    public void setConsommation(Double consommation) {
        this.consommation = consommation;
    }

}
