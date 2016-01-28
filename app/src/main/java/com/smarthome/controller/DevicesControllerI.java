package com.smarthome.controller;

import com.smarthome.model.DevicesModelI;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface DevicesControllerI extends SmartHomeControllerI {

    public DevicesModelI getDevicesModel();
    public void deleteDevice(int piecePosition,int devicePosition) throws Exception;
    public void createNewDevice(int positionPiece,String name, String address);


}
