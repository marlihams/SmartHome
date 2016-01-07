package com.smarthome.android;

import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.smarthome.R;
import com.smarthome.controller.DevicesController;
import com.smarthome.controller.HousesController;
import com.smarthome.view.DeviceObserver;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        super.initialize();
        lContext=this;
        // creation controller view model
        initializeMvc();

        //getting object from their Ids
        expandableListdevices= (ExpandableListView)findViewById(R.id.list_expandable_device);
        addDevice=(ImageButton)findViewById(R.id.add);
        deleteDevice=(ImageButton)findViewById(R.id.delete);
        titleActivity=(TextView)findViewById(R.id.titleActivity);
        titleActivity.setText("Devices ");
        devicesView.initializeWidget(expandableListdevices, addDevice, deleteDevice);
        devicesView.setListener();
    }

    @Override
    public void initializeMvc() {

        DevicesController devicesController=new DevicesController();
        devicesView= (DevicesView)((DevicesController)devicesController).getView();
    }


}