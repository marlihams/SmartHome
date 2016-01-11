package com.smarthome.model;

import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.view.HouseObserver;

import java.util.List;
import java.util.Map;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface HouseDetailModelI {
    public House getHouse();
    public List<Device> getDevices();
    public void subscribeHouseObserver(HouseObserver observer);
    public void notifyHouseObserver();
    public void updateHouse(String name,String address);
    public Map getHouseDetail();
    public List<Historique> getHouseHistorique();
    public List<Historique> getLastConsumptionsByHouse();
}
