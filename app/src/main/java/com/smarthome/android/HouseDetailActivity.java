package com.smarthome.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smarthome.R;
import com.smarthome.controller.HouseDetailController;
import com.smarthome.controller.HouseDetailControllerI;
import com.smarthome.model.HouseDetailModel;
import com.smarthome.model.HouseDetailModelI;
import com.smarthome.view.HouseDetailView;
import com.smarthome.view.HousesView;
import com.smarthome.view.SmartHomeView;

public class HouseDetailActivity extends SmartMenuActivity implements SmartHomeView {




    private HouseDetailView houseDetailView;
    private HouseDetailControllerI houseDetailController;
    private HouseDetailModelI houseDetailModel;

    private EditText houseName;
    private  EditText houseAddress;
    private TextView   nbDevice;
    private TextView nbTurnOn;
    private  TextView nbTurnOff;
    private  TextView nbBroke;
    private Spinner historiqueDate;
    private TextView  consoPeriode;
    private int houseId;
    private ImageButton submit;
    private TextView title;
    private LinearLayout monthlyConsumptionChart;
    private LinearLayout lastMonthComparisonChart;
    private TextView comparisonChartTitle;
    private EditText routeur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        super.initialize();

        // creation controller view model

        Intent intent=getIntent();
        title=(TextView)findViewById(R.id.title);
        title.setText("House Detail");
         houseId=intent.getIntExtra(HousesView.SELECTEDHOUSE,0);
        houseName=(EditText)findViewById(R.id.house_name);
        houseAddress=(EditText)findViewById(R.id.house_address);
        nbDevice=(TextView)findViewById(R.id.nb_device);
        nbTurnOn=(TextView)findViewById(R.id.device_turnon);
        nbTurnOff=(TextView)findViewById(R.id.device_turnoff);
        historiqueDate=(Spinner)findViewById(R.id.conso_date);
        consoPeriode=(TextView)findViewById(R.id.consommation);
        submit=(ImageButton)findViewById(R.id.submit);
        routeur=(EditText)findViewById(R.id.router_address);
        monthlyConsumptionChart =(LinearLayout)findViewById(R.id.monthlyConsumptionChart);
        lastMonthComparisonChart =(LinearLayout)findViewById(R.id.lastMonthComparisonChart);
        comparisonChartTitle = (TextView) findViewById(R.id.comparisonChartTitle);
        //getting object from their Ids

        initializeMvc();
        houseDetailView.initializeWidget(houseName, houseAddress, nbDevice, nbTurnOff, nbTurnOn,
                historiqueDate, consoPeriode, submit, monthlyConsumptionChart, lastMonthComparisonChart,
                comparisonChartTitle,routeur);

        houseDetailView.setListener();

    }

    @Override
    public void initializeMvc() {
        houseDetailModel =new HouseDetailModel(houseId);
        houseDetailController= new HouseDetailController(houseDetailModel);
        houseDetailView=(HouseDetailView)houseDetailController.getView();
    }


    @Override
    public void onBackPressed(){

        startActivity(new Intent(this, HousesActivity.class));
    }


}
