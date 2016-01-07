package com.smarthome.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.smarthome.R;
import com.smarthome.controller.AcceuilController;
import com.smarthome.controller.AcceuilControllerI;
import com.smarthome.view.AcceuilView;
import com.smarthome.view.SmartHomeView;

public class AcceuilActivity extends SmartMenuActivity implements SmartHomeView {

    private AcceuilView acceuilView;

        private ImageButton profil;
        private ImageButton homes;
        private ImageButton devices;


    @Override
    public void initializeMvc() {

        AcceuilControllerI acceuilController=new AcceuilController();
        acceuilView=((AcceuilController)acceuilController).getAcceuilView();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_connexion);
         super.initialize();


         profil=(ImageButton)findViewById(R.id.profil_acceuil);
         homes=(ImageButton)findViewById(R.id.home_acceuil);
         devices=(ImageButton)findViewById(R.id.device_acceuil);

        initializeMvc();
        acceuilView.initializeWidget(profil, homes, devices);
        acceuilView.setListener();

    }
    @Override
    public void onBackPressed() {
        //TODO update the database here
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setResult(100);
                        finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
