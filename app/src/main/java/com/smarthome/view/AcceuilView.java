package com.smarthome.view;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.controller.AcceuilControllerI;
import com.smarthome.database.PreferenceManager;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class  AcceuilView implements SmartView {

     private AcceuilControllerI acceuilController;

    private ImageButton profil;
    private ImageButton homes;
    private ImageButton devices;
    private ImageButton preferences;
    int houseId;

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    @Override
    public void initializeWidget(View... views) {
            profil=(ImageButton)views[0];
             homes=(ImageButton)views[1];
            devices=(ImageButton) views[2];
            preferences=(ImageButton) views[3];
            initializeCOnnectionMode();
    }

    private void initializeCOnnectionMode() {
        PreferenceManager pref=PreferenceManager.getInstance(AcceuilActivity.getlContext());
        String mode=pref.findElement(PreferencesView.MODECONNECTION);
        if (!mode.equals(PreferenceManager.DEFAULT)){
            if (mode.equals("router"))
                SmartChangeView.setBluetooth(false);
            else
                SmartChangeView.setBluetooth(true);
        }
    }

    @Override
    public void setListener() {
        SmartAnimation.init(AcceuilActivity.getlContext());
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.wave_scale);
                SmartChangeView.changeView(AcceuilActivity.getlContext(), "profil");
            }
        });
        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.wave_scale);
                if (!SmartChangeView.isBluetooth() && !SmartChangeView.isRouteur() && !BluetoothAdapter.getDefaultAdapter().isEnabled() )
                    Toast.makeText(AcceuilActivity.getlContext(),"Bluethoof or Router mode should be activated first see Menu Preferences",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(AcceuilActivity.getlContext(), DevicesActivity.class);
                    intent.putExtra(HousesView.SELECTEDHOUSE, houseId);
                    AcceuilActivity.getlContext().startActivity(intent);
                }

            }
        });
        homes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.wave_scale);

                SmartChangeView.changeView(AcceuilActivity.getlContext(), "houses");

            }
        });
        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.wave_scale);

                SmartChangeView.changeView(AcceuilActivity.getlContext(), "preferences");

            }
        });

    }

    @Override
    public void subscribeObserver() {

    }
}
