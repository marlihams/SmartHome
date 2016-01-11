package com.smarthome.model;


import com.smarthome.view.DeviceObserver;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public interface DevicesModelI {

    public  void subscribeDeviceObserver(DeviceObserver observer);
    public void notifyDevicesObserver();


}
