package com.smarthome.controller;

import com.smarthome.model.DevicesModelI;
import com.smarthome.model.HousesModelI;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface DevicesControllerI extends SmartHomeControllerI {

    public DevicesModelI getDevicesModel();
    public  void deleteDevice(List<Integer> position);

    public   void  createNewDevice(String name,String address);

}
