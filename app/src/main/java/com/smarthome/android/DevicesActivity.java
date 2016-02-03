package com.smarthome.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.R;
import com.smarthome.controller.DevicesController;
import com.smarthome.model.DevicesModel;
import com.smarthome.model.DevicesModelI;
import com.smarthome.view.DevicesView;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartHomeView;

/**
 * Created by Mdiallo on 23/12/2015.
 */
public class DevicesActivity extends  SmartMenuActivity implements SmartHomeView {


    private DevicesView devicesView;
    private ExpandableListView expandableListdevices;
    private ImageButton addDevice;
    private ImageButton deleteDevice;
    private TextView titleActivity;
    private  ImageButton scanDevice;
    int houseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        super.initialize();
        lContext=this;
        // creation controller view model
        Intent intent=getIntent();
        houseId=intent.getIntExtra(HousesView.SELECTEDHOUSE,0);



        //getting object from their Ids
        expandableListdevices= (ExpandableListView)findViewById(R.id.list_expandable_device);
        scanDevice=(ImageButton)findViewById(R.id.scandevice);
        addDevice=(ImageButton)findViewById(R.id.add);
        deleteDevice=(ImageButton)findViewById(R.id.delete);
        titleActivity=(TextView)findViewById(R.id.titleActivity);
        titleActivity.setText("Devices ");

        try {
            initializeMvc();
        } catch (Exception e) {
            Toast.makeText(getlContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        devicesView.initializeWidget(expandableListdevices, addDevice, deleteDevice,scanDevice);
        devicesView.setListener();
    }

    @Override
    public void initializeMvc() throws Exception{
        DevicesModelI devicesModel=new DevicesModel(houseId);
        DevicesController devicesController=new DevicesController(devicesModel);
        devicesView= (DevicesView)((DevicesController)devicesController).getView();
    }


}