package com.smarthome.controller;

import com.smarthome.model.HousesModel;
import com.smarthome.model.HousesModelI;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartView;
import com.smarthome.beans.House;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class HousesController implements  HousesControllerI {
    private HousesView housesView;
    private HousesModelI housesModel;


    public HousesController(HousesModelI housesModel) {
        housesView=new HousesView(this,housesModel);
        this.housesModel=housesModel;
    }

    @Override
    public SmartView getView() {
        return housesView;
    }

    @Override
    public void deleteHouse(List<Integer> position){
       for (int i=0,len=position.size();i<len;i++){
           housesModel.getAdapter().removeItem(position.get(i));
       }
        housesModel.updateCache();
    }

    @Override
    public HousesModelI getHousesModel() {
        return housesModel;
    }

    @Override
    public List<String> getHousesName(){

        List<String> housesName=new ArrayList<String>();
        List<House> houses=housesModel.getHouses();
        for (int i=0,len=houses.size();i<len;i++){
            housesName.add(houses.get(i).getName());
        }
        return housesName;
    }
    public   void  createNewHouse(String name,String address){

        House house=new House(name,address,housesModel.getHouses().get(0).getUser());
        //   housesController.getHousesModel().getAdapter().addItem();
        housesModel.updateAdapter(house, "0");
    }
}
