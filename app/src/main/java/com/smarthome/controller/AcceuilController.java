package com.smarthome.controller;

import com.smarthome.view.AcceuilView;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public class AcceuilController implements AcceuilControllerI {
    private AcceuilView acceuilView;

    public AcceuilController() {
        acceuilView=new AcceuilView();
    }

    public AcceuilView getAcceuilView() {
        return acceuilView;
    }
}
