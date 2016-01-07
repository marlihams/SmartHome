package com.smarthome.model;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.House;
import com.smarthome.view.DeviceObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public class DevicesModel implements  DevicesModelI{

    private  List<Device> houses;
    private DeviceCacheDao deviceCacheDao;
    private List<DeviceObserver> deviceObservers=new ArrayList<DeviceObserver>();

    @Override
    public void subscribeDeviceObserver(DeviceObserver observer) {
        deviceObservers.add(observer);

    }

    @Override
    public void notifyDevicesObserver() {
        // TODO notify the expandable list when needeed

    }

    public DevicesModel() {
        deviceCacheDao=new DeviceCacheDao(DevicesActivity.getlContext());
    }
}
