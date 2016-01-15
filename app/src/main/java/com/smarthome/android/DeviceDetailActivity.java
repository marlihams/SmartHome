package com.smarthome.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smarthome.R;
import com.smarthome.controller.DeviceDetailController;
import com.smarthome.controller.DeviceDetailControllerI;
import com.smarthome.model.DeviceDetailModel;
import com.smarthome.model.DeviceDetailModelI;
import com.smarthome.view.DeviceDetailView;
import com.smarthome.view.DevicesView;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartHomeView;

import java.util.List;

/**
 * Created by Mdiallo on 23/12/2015.
 */
public class DeviceDetailActivity extends  SmartMenuActivity implements SmartHomeView {
    private DeviceDetailView deviceDetailView;
    private DeviceDetailControllerI deviceDetailController;
    private DeviceDetailModelI deviceDetailModel;
    private int deviceId;
    private TextView title;
    private EditText deviceName;
    private  EditText pieceName;
    private Spinner historiqueDate;
    private TextView  consoPeriode;
    private ImageButton submit;

    private LinearLayout consodeviceMonthly;
    private LinearLayout consodeviceComparaison;
    private LinearLayout consodeviceChartCircle;
    private TextView comparisonChartTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        super.initialize();
        Intent intent=getIntent();
        title=(TextView)findViewById(R.id.title);
        title.setText("Device Detail");
        deviceId=intent.getIntExtra(DevicesView.SELECTEDDEVICE,0);
        pieceName=(EditText)findViewById(R.id.pieceName);
        submit=(ImageButton)findViewById(R.id.submit);
        deviceName=(EditText)findViewById(R.id.deviceName);
        historiqueDate=(Spinner)findViewById(R.id.conso_device);
        consoPeriode=(TextView)findViewById(R.id.consommation);
        consodeviceMonthly=(LinearLayout)findViewById(R.id.monthlyConsumptionChart);
        consodeviceComparaison=(LinearLayout)findViewById(R.id.lastMonthComparisonChart);
        consodeviceChartCircle=(LinearLayout)findViewById(R.id.lastMonthComparisonChartCircle);
        comparisonChartTitle=(TextView)findViewById(R.id.comparisonChartTitle);
        // creation controller view model
        initializeMvc();

        //getting object from their Ids
        deviceDetailView.initializeWidget(pieceName, deviceName, submit,historiqueDate, consoPeriode, consodeviceMonthly,
                consodeviceChartCircle,comparisonChartTitle,consodeviceComparaison);
        deviceDetailView.setListener();

    }

    @Override
    public void initializeMvc() {
        deviceDetailModel =new DeviceDetailModel(deviceId);
        deviceDetailController= new DeviceDetailController(deviceDetailModel);
        deviceDetailView=(DeviceDetailView)deviceDetailController.getView();


    }

    public static Context getlContext() {
        return lContext;
    }
}