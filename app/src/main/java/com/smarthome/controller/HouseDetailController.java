package com.smarthome.controller;

import com.smarthome.beans.House;
import com.smarthome.model.HouseDetailModel;
import com.smarthome.model.HouseDetailModelI;
import com.smarthome.model.HousesModel;
import com.smarthome.model.HousesModelI;
import com.smarthome.view.HouseDetailView;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class HouseDetailController implements  HouseDetailControllerI {
    private HouseDetailView houseDetailView;
    private HouseDetailModelI houseDetailModel;


    public HouseDetailController(HouseDetailModelI houseDetailModel ) {
        this.houseDetailModel=houseDetailModel;
        houseDetailView=new HouseDetailView(this,houseDetailModel);

    }

    @Override
    public SmartView getView() {
        return houseDetailView;
    }



    @Override
    public HouseDetailModelI getHouseDetailModel() {
       return houseDetailModel;
    }
}
