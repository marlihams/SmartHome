package com.smarthome.model;


import com.smarthome.beans.House;
import com.smarthome.view.DeviceObserver;

import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public interface DevicesModelI {

    public  void subscribeDeviceObserver(DeviceObserver observer);
    public void notifyDevicesObserver();


}
