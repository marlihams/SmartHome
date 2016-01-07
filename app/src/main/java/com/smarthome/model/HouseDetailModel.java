package com.smarthome.model;

import com.smarthome.BeanCache.ConsoHouseCacheDao;
import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.android.HouseDetailActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.view.HouseObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Mdiallo on 19/12/2015.
 */
public class HouseDetailModel implements HouseDetailModelI{
    private HouseCacheDao houseCacheDao;
    private House house;
    private DeviceCacheDao deviceCacheDao;
    private HistoriqueCacheDao historiqueCacheDao;
    private List<HouseObserver> houseObservers=new ArrayList<HouseObserver>();
    private List<Historique> historiques;



    private List<Device> devices;

    public HouseDetailModel(int homeId) {
     houseCacheDao=new HouseCacheDao(HouseDetailActivity.getlContext());
        deviceCacheDao=new DeviceCacheDao(HouseDetailActivity.getlContext());
        historiqueCacheDao=new HistoriqueCacheDao(HouseDetailActivity.getlContext());
        house=houseCacheDao.findByPkey((Object)homeId);
        historiques=historiqueCacheDao.getHistoriqueByHouse(homeId);
        devices=deviceCacheDao.findAllByForeignKey(house.getId(), "house");
    }

    @Override
    public House getHouse() {
        return house;
    }

    @Override
    public void subscribeHouseObserver(HouseObserver observer) {
        this.houseObservers.add(observer);
    }

    @Override
    public void notifyHouseObserver() {
        for (int i=0;i<houseObservers.size();i++){
            houseObservers.get(i).updateHouseObserver();
        }
    }

    @Override
    public void updateHouse(String name,String address){
        house.setName(name);
        house.setAddress(address);
        notifyHouseObserver();
        updateAllDevice();

    }

    @Override
    public Map getHouseDetail() {
       Map housesInfo=new HashMap();
    //TODO calculate properly this map  by communicating with devices

        housesInfo.put("broke",4);
        housesInfo.put("turnon",2);
        housesInfo.put("turnoff",22);
        return housesInfo;
    }

    @Override
    public List<Historique> getHouseHistorique() {

       return historiques;
    }

    private  void updateAllDevice(){
        List<Device>allDevices=deviceCacheDao.findAll();
        allDevices.removeAll(devices);
        for (int i=0,len=devices.size();i<len;i++){
            devices.get(i).setHouse(house);
        }
        allDevices.addAll(devices);
        deviceCacheDao.addElements(allDevices);
    }

    @Override
    public List<Device> getDevices() {
        return devices;
    }
}
