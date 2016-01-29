package com.smarthome.model;


import com.smarthome.beans.Device;
import com.smarthome.view.DeviceObserver;

import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public interface DevicesModelI {

    public  void subscribeDeviceObserver(DeviceObserver observer);
    public void notifyDevicesObserver();
    public List<Device> getDevices();
    public DeviceListAdapter getDeviceListAdapter();
    public void updateAdapter(Device device);
    public void updateDevice();
    public void notifySwitchObserver(int parent , int child,boolean ischecked) throws Exception;
    public Device findDeviceIdAdapter(int parentPosition,int childPosition);
}
