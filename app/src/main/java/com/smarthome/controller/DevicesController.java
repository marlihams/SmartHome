package com.smarthome.controller;

import com.smarthome.model.DevicesModel;
import com.smarthome.model.DevicesModelI;
import com.smarthome.view.DevicesView;
import com.smarthome.view.SmartView;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DevicesController implements DevicesControllerI {

    private DevicesView devicesView;
    private DevicesModelI devicesModel;


    public DevicesController() {

        devicesModel=new DevicesModel();
        devicesView=new DevicesView(this,devicesModel);
    }

    @Override
    public SmartView getView() {
        return devicesView;
    }



}
