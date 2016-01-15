package com.smarthome.model;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.android.HouseDetailActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.view.HouseObserver;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        historiques=historiqueCacheDao.findAllByForeignKey(homeId,"house_id");
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
        houseCacheDao.createOrUpdate(house);

    }

    @Override
    public Map getHouseDetail() {
       Map housesInfo=new HashMap();
    //TODO calculate properly this map  by communicating with devices

        housesInfo.put("broke", 4);
        housesInfo.put("turnon", 2);
        housesInfo.put("turnoff", 22);
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

    @Override
    public List<Historique> getLastConsumptionsByHouse() {
        List<Historique> lastConsumptionByHouse = new ArrayList<Historique>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");
        //récupérer la dernière période pendant laquelle il y a eu des consommations
        List<Historique> all = historiqueCacheDao.findAll();
        DateTime lastPeriod = formatter.parseDateTime(all.get(0).getPeriode());
        for(int i = 1; i < all.size(); i++) {
            if(formatter.parseDateTime(all.get(i).getPeriode()).isBefore(lastPeriod)) {
                lastPeriod = formatter.parseDateTime(all.get(i).getPeriode());
            }
        }

        for(Historique historique : historiqueCacheDao.findAll()) {
            if(historique.getHouse() != null && historique.getPeriode().equals(formatter.print(lastPeriod))){
                lastConsumptionByHouse.add(historique);
            }
        }
        return lastConsumptionByHouse;
    }
}
