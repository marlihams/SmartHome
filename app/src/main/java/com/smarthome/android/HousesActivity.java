package com.smarthome.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.smarthome.R;
import com.smarthome.controller.AcceuilController;
import com.smarthome.controller.AcceuilControllerI;
import com.smarthome.controller.HousesController;
import com.smarthome.model.HousesModel;
import com.smarthome.model.HousesModelI;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartHomeView;

/**
 * Created by Mdiallo on 23/12/2015.
 */
public class HousesActivity extends  SmartMenuActivity implements SmartHomeView {


    private HousesView housesView;
    private RecyclerView listHouses;
    private ImageButton addHouse;
    private ImageButton refresh;
    private ImageButton deleteHouse;
    private TextView titleActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        super.initialize();
    
        // creation controller view model
        initializeMvc();

        //getting object from their Ids
        listHouses= (RecyclerView)findViewById(R.id.list);
        addHouse=(ImageButton)findViewById(R.id.add);
        deleteHouse=(ImageButton)findViewById(R.id.delete);
        refresh=(ImageButton)findViewById(R.id.refresh);
        titleActivity=(TextView)findViewById(R.id.titleActivity);
        titleActivity.setText("Houses ");
        housesView.initializeWidget(listHouses,addHouse,deleteHouse,refresh);
        housesView.setListener();
    }

    @Override
    public void initializeMvc() {
        HousesModelI housesModel=new HousesModel();
        HousesController housesController=new HousesController(housesModel);
        housesView= (HousesView)((HousesController)housesController).getView();
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,AcceuilActivity.class));
    }


}