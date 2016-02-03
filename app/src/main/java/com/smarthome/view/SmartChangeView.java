package com.smarthome.view;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.PreferencesActivity;
import com.smarthome.android.UserActivity;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public  class SmartChangeView {

    private static boolean  bluetooth=false;
    private static boolean routeur =false;
    private static int houseId=0;

    public static int getHouseId() {
        return houseId;
    }

    public static void setHouseId(int houseId) {
        SmartChangeView.houseId = houseId;
    }

    public static boolean isBluetooth() {
        return bluetooth;
    }
    public static boolean isRouteur() {
        return routeur;
    }
    public static void setBluetooth(boolean bluetooth) {
        SmartChangeView.bluetooth = bluetooth;
        SmartChangeView.routeur=!bluetooth;
    }

    public static void changeView(Context context,String destination){

        Intent intent=null;



        switch (destination){
            case "acceuil":
                intent=new Intent(context, AcceuilActivity.class);
                break;
            case "devices":
                intent=new Intent(context, DevicesActivity.class);
                break;
            case "houses":
                intent=new Intent(context, HousesActivity.class);
                break;
            case "profiles":
                intent=new Intent(context, UserActivity.class);
                break;

                case "preferences":
                intent=new Intent(context, PreferencesActivity.class);
                break;
            default:
                break;

        }
        if (destination.equals("houses") || destination.equals("devices")) {
            if (!SmartChangeView.isBluetooth() && !SmartChangeView.isRouteur())
                Toast.makeText(context, "Bluethoof or Router mode should be activated first see Menu Preferences", Toast.LENGTH_SHORT).show();
            else
                context.startActivity(intent);
        }
         else
            context.startActivity(intent);
    }

}
