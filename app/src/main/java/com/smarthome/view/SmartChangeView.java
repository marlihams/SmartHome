package com.smarthome.view;

import android.content.Context;
import android.content.Intent;

import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.UserActivity;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public  class SmartChangeView {

    public static boolean  bluetooth=false;

    public static boolean isBluetooth() {
        return bluetooth;
    }

    public static void setBluetooth(boolean bluetooth) {
        SmartChangeView.bluetooth = bluetooth;
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
             //   intent=new Intent(context, PreferenceActivity.class);
                break;
            default:
                break;

        }
        context.startActivity(intent);
    }

}
