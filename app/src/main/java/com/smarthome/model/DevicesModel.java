package com.smarthome.model;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.android.DevicesActivity;
import com.smarthome.beans.Device;
import com.smarthome.view.DeviceObserver;
import com.smarthome.beans.House;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public class DevicesModel implements  DevicesModelI{

    private  List<Device> devices;
    private DeviceCacheDao deviceCacheDao;
    private HouseCacheDao houseCacheDao;
    private List<DeviceObserver> deviceObservers=new ArrayList<DeviceObserver>();


    private DeviceListAdapter deviceListAdapter;
    private House house;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public DevicesModel(int houseId) {
        deviceCacheDao=new DeviceCacheDao(DevicesActivity.getlContext());
        houseCacheDao=new HouseCacheDao(DevicesActivity.getlContext());
        house=houseCacheDao.findByPkey(houseId);
        devices=deviceCacheDao.findAllByForeignKey(houseId, "home_id");
        deviceListAdapter=null;
//        public DeviceListAdapter(Context context, List<String> listPieces,HashMap<String, List<Boolean>>  listSwitch,
//                HashMap<String, List<String>> listDevices) {

        initializeAdapter();

    }

    private void initializeAdapter() {

        List<String>pieceName=buildPieces();
        HashMap<String,List<Boolean>> deviceEtat=buildDeviceEtat(pieceName);
        HashMap<String,List<String>> listdevicesName=buildDevicesName(pieceName);

        deviceListAdapter=new DeviceListAdapter(DevicesActivity.getlContext(),pieceName,deviceEtat,listdevicesName);
        deviceListAdapter.setDevicesModel(this);

    }

    private HashMap<String, List<String>> buildDevicesName(List<String>pieceName) {

        HashMap<String,List<String>> deviceName=new HashMap<>();
        for (int i=0,len=pieceName.size();i<len;i++){
            deviceName.put(pieceName.get(i),new ArrayList<String>());
        }
        String key;
        List<String> list;
        for (int i=0,len=devices.size();i<len;i++){
            key=devices.get(i).getPieceName();
            list=deviceName.get(key);
            list.add(devices.get(i).getName());
            deviceName.put(key,list);
        }

        return deviceName;

    }

    private HashMap<String, List<Boolean>> buildDeviceEtat(List<String>pieceName) {

        HashMap<String,List<Boolean>> deviceEtat=new HashMap<>();
        for (int i=0,len=pieceName.size();i<len;i++){
            deviceEtat.put(pieceName.get(i),new ArrayList<Boolean>());
        }
        String deviceName;
        List<Boolean> etat;
        for (int i=0,len=devices.size();i<len;i++){
            deviceName=devices.get(i).getPieceName();
             etat=deviceEtat.get(deviceName);
                etat.add(getEtatDevice(devices.get(i)));
            deviceEtat.put(deviceName,etat);
        }
            return deviceEtat;

    }

    private Boolean getEtatDevice(Device device) {

        //TODO communicate with the device to see if it is on or off
        int a=(int)(Math.random()*(10));
        return a<=5? false:true;
    }

    private List<String> buildPieces() {
        List<String> pieceName=new ArrayList<String>();
        String n;
        for (int i=0,len=devices.size();i<len;i++){
            n=devices.get(i).getPieceName();
            if (!pieceName.contains(n))
                pieceName.add(n);
        }
        return pieceName;
    }

    @Override
    public void notifySwitchObserver(int parent , int child,boolean ischecked) {
        for (int i=0;i<deviceObservers.size();i++){
            deviceObservers.get(i).updateDeviceLightObserver(parent,child,ischecked);
        }
    }

    @Override
    public void subscribeDeviceObserver(DeviceObserver observer) {
        deviceObservers.add(observer);

    }

    @Override
    public void notifyDevicesObserver() {
        // TODO notify the expandable list when needeed
        for (int i=0;i<deviceObservers.size();i++){
          deviceObservers.get(i).updateDeviceObserver();
        }

    }


    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public DeviceListAdapter getDeviceListAdapter() {
        return deviceListAdapter;
    }

    @Override
    public void updateAdapter(Device device) {

        deviceListAdapter.addItem(device.getPieceName(),device.getName(),getEtatDevice(device));}

    @Override
    public void updateDevice() {

    }
}
