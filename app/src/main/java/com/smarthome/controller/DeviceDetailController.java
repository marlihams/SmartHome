package com.smarthome.controller;

import com.smarthome.beans.Historique;
import com.smarthome.model.DeviceDetailModelI;
import com.smarthome.view.DeviceDetailView;
import com.smarthome.view.SmartView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DeviceDetailController implements DeviceDetailControllerI {
    private DeviceDetailView deviceDetailView;
    private DeviceDetailModelI deviceDetailModel;


    public DeviceDetailController(DeviceDetailModelI deviceDetailModel ) {
        this.deviceDetailModel=deviceDetailModel;
        deviceDetailView=new DeviceDetailView(this,deviceDetailModel);

    }

    @Override
    public SmartView getView() {
        return deviceDetailView;
    }



    @Override
    public DeviceDetailModelI getDeviceDetailModel() {
        return deviceDetailModel;
    }

    @Override
    public List<Historique> getSortedHistoriquesByDate() {
        List<Historique> sortedHistoriques = getDeviceDetailModel().getDeviceHistorique();

        Collections.sort(sortedHistoriques, new Comparator<Historique>() {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");

            @Override
            public int compare(Historique one, Historique two) {
                return formatter.parseDateTime(two.getPeriode()).compareTo(formatter.parseDateTime(one.getPeriode()));
            }
        });
        return sortedHistoriques;
    }

}
