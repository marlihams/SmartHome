package com.smarthome.controller;

import com.smarthome.beans.Historique;
import com.smarthome.model.HouseDetailModelI;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface HouseDetailControllerI extends SmartHomeControllerI {


    HouseDetailModelI getHouseDetailModel();

    //public List<String> getHousesName();

    public List<Historique> getSortedHistoriquesByDate();
}

