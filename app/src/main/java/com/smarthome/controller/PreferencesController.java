package com.smarthome.controller;

import com.smarthome.model.PreferencesModelI;
import com.smarthome.view.PreferencesView;
import com.smarthome.view.SmartView;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class PreferencesController implements PreferencesControllerI {
    private PreferencesView preferencesView;
    private PreferencesModelI preferencesModel;


    public PreferencesController(PreferencesModelI preferencesModel) {
        this.preferencesModel=preferencesModel;
        preferencesView=new PreferencesView(this);

    }

    @Override
    public SmartView getView() {
        return preferencesView;
    }


    public PreferencesModelI getPreferencesModel() {
        return preferencesModel;
    }

    public void setPreferencesModel(PreferencesModelI preferencesModel) {
        this.preferencesModel = preferencesModel;
    }



}
