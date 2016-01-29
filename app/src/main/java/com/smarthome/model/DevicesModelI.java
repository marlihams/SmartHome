package com.smarthome.model;


import com.smarthome.beans.Device;
import com.smarthome.beans.House;
import com.smarthome.electronic.ElectronicManager;
import com.smarthome.electronic.RouteurManager;
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
    public House getHouse();
    public void notifySwitchObserver(int parent , int child,boolean ischecked);
    public Device findDeviceIdAdapter(int parentPosition,int childPosition);

    
    public void createNewDevice(int positionPiece, String name, String address);

   public  void deleteDevice(int piecePosition, int devicePosition);
}
