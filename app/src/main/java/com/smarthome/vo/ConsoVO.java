package com.smarthome.vo;

import com.smarthome.beans.Historique;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

/**
 * Created by Amstrong on 10/1/2016.
 */
public class ConsoVO {
    private String mmYear;
    private Double consommation;
    private String name;


    public ConsoVO(Historique historique,boolean bool) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM-yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("00");
        DateTime date = dateFormatter.parseDateTime(historique.getPeriode());
        mmYear = decimalFormat.format(date.getMonthOfYear())+ "/" + historique.getPeriode().substring(historique.getPeriode().length() - 2);
        consommation = Double.valueOf(historique.getConsommation());
        name =  bool ? historique.getHouse().getName():historique.getDevice().getName();
    }


    public String getMmYear() {
        return mmYear;
    }

    public Double getConsommation() {
        return consommation;
    }

    public String getName() {
        return name;
    }
}
