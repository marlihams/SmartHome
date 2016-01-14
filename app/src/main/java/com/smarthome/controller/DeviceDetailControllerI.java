package com.smarthome.controller;

import com.smarthome.beans.Historique;
import com.smarthome.model.DeviceDetailModelI;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface DeviceDetailControllerI extends SmartHomeControllerI {
    DeviceDetailModelI getDeviceDetailModel();

    //public List<String> getDevicesName();

    public List<Historique> getSortedHistoriquesByDate();
}
