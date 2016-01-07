package com.smarthome.controller;

import com.smarthome.model.HousesModelI;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartView;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface HousesControllerI extends SmartHomeControllerI {


   public  void deleteHouse(List<Integer> position);

    HousesModelI getHousesModel();
    public   void  createNewHouse(String name,String address);

    public List<String> getHousesName();
}

