package com.smarthome.view;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.smarthome.R;
import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.UserActivity;
import com.smarthome.controller.AcceuilController;
import com.smarthome.controller.AcceuilControllerI;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class  AcceuilView implements SmartView {

     private AcceuilControllerI acceuilController;

    private ImageButton profil;
    private ImageButton homes;
    private ImageButton devices;



    @Override
    public void initializeWidget(View... views) {
            profil=(ImageButton)views[0];
             homes=(ImageButton)views[1];
            devices=(ImageButton) views[2];

    }

    @Override
    public void setListener() {

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartChangeView.changeView(AcceuilActivity.getlContext(),"profil");
            }
        });
        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    SmartChangeView.changeView(DevicesActivity.getlContext(),"devices");

            }
        });
        homes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       SmartChangeView.changeView(HomesActivity.getlContext(),"homes");

            }
        });

    }
}