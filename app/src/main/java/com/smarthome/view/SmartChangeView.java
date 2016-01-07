package com.smarthome.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.smarthome.R;
import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.UserActivity;
import com.smarthome.beans.User;

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
