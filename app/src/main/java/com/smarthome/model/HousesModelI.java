package com.smarthome.model;


import com.smarthome.beans.House;
import com.smarthome.view.HouseObserver;

import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public interface  HousesModelI {

    public List<House> getHouses();
    public void subscribeHouseObserver(HouseObserver observer);
    public void notifyHouseObserver();
    public HomeAdapter getAdapter();
    public void setAdapter(HomeAdapter adapter);
    public  void updateAdapter(House house,String conso);
    public  void initializeAdapter();
    public void updateCache();
}
