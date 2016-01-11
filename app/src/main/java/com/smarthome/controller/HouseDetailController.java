package com.smarthome.controller;

import com.smarthome.beans.Historique;
import com.smarthome.model.HouseDetailModelI;
import com.smarthome.view.HouseDetailView;
import com.smarthome.view.SmartView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public List<Historique> getSortedHistoriquesByDate() {
        List<Historique> sortedHistoriques = getHouseDetailModel().getHouseHistorique();

        Collections.sort(sortedHistoriques, new Comparator<Historique>() {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");

            @Override
            public int compare(Historique one, Historique two) {
                return formatter.parseDateTime(two.getPeriode()).compareTo(formatter.parseDateTime(one.getPeriode()));
            }
        });
        return sortedHistoriques;
    }
}
