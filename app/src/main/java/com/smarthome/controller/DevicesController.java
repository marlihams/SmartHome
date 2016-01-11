package com.smarthome.controller;

import com.smarthome.model.DevicesModel;
import com.smarthome.model.DevicesModelI;
import com.smarthome.view.DevicesView;
import com.smarthome.view.SmartView;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DevicesController implements DevicesControllerI {

    private DevicesView devicesView;
    private DevicesModelI devicesModel;

    public DevicesModelI getDevicesModel() {
        return devicesModel;
    }

    @Override
    public void deleteDevice(List<Integer> position) {

    }

    @Override
    public void createNewDevice(String name, String address) {

    }

    public void setDevicesModel(DevicesModelI devicesModel) {
        this.devicesModel = devicesModel;
    }

    public DevicesController(DevicesModelI devicesModel) {

        this.devicesModel=devicesModel;
        devicesView=new DevicesView(this,devicesModel);
    }

    @Override
    public SmartView getView() {
        return devicesView;
    }

}
