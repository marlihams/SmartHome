package com.smarthome.view;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface DeviceObserver {
    void updateDeviceObserver();
    void updateDeviceLightObserver(int parent ,int child,boolean ischecked) throws Exception;
}
