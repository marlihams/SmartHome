package com.smarthome.controller;

import com.smarthome.model.DevicesModelI;
import com.smarthome.view.DevicesView;
import com.smarthome.view.SmartView;

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
    public void deleteDevice(int piecePosition,int devicePosition) throws Exception{

            if (piecePosition==-1)
                throw new Exception("select a device first");
            else
            devicesModel.deleteDevice(piecePosition,devicePosition);
    }

    @Override
    public void createNewDevice(int positionPiece,String name, String address) {

        devicesModel.createNewDevice(positionPiece,name,address);
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
