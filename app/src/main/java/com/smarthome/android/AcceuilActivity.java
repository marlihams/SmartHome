package com.smarthome.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.smarthome.R;
import com.smarthome.controller.AcceuilController;
import com.smarthome.controller.AcceuilControllerI;
import com.smarthome.view.AcceuilView;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartHomeView;

public class AcceuilActivity extends SmartMenuActivity implements SmartHomeView {

    private AcceuilView acceuilView;

        private ImageButton profil;
        private ImageButton homes;
        private ImageButton devices;
        int houseId;


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

        Intent intent=getIntent();
        houseId=intent.getIntExtra(HousesView.SELECTEDHOUSE,0);

         profil=(ImageButton)findViewById(R.id.profil_acceuil);
         homes=(ImageButton)findViewById(R.id.home_acceuil);
         devices=(ImageButton)findViewById(R.id.device_acceuil);

        initializeMvc();
        acceuilView.initializeWidget(profil, homes, devices);
        acceuilView.setListener();
        acceuilView.setHouseId(houseId);

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
//                        ((Activity) LoginActivity.getlContext()).finish();
//                        ((Activity) HousesActivity.getlContext()).finish();
                     Intent i=  new Intent(AcceuilActivity.this,LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("finish",1);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
