package com.smarthome.controller;

import com.smarthome.model.HouseDetailModel;
import com.smarthome.model.HouseDetailModelI;
import com.smarthome.model.HousesModelI;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface HouseDetailControllerI extends SmartHomeControllerI {


    HouseDetailModelI getHouseDetailModel();

    //public List<String> getHousesName();
}

