package com.smarthome.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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



    private static Context lContext;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        super.initialize();
        lContext=this;
        // creation controller view model

        Intent intent=getIntent();
        title=(TextView)findViewById(R.id.title);
        title.setText("House Detail");
         houseId=intent.getIntExtra(HousesView.SELECTEDHOUSE,0);
        houseName=(EditText)findViewById(R.id.house_name);
        houseAddress=(EditText)findViewById(R.id.house_address);
        nbDevice=(TextView)findViewById(R.id.nb_device);
        nbBroke=(TextView)findViewById(R.id.device_broke);
        nbTurnOn=(TextView)findViewById(R.id.device_turnon);
        nbTurnOff=(TextView)findViewById(R.id.device_turnoff);
        historiqueDate=(Spinner)findViewById(R.id.conso_date);
        consoPeriode=(TextView)findViewById(R.id.consommation);
        submit=(ImageButton)findViewById(R.id.submit);

        //getting object from their Ids

        initializeMvc();
        houseDetailView.initializeWidget(houseName,houseAddress,nbDevice,nbBroke,nbTurnOff,nbTurnOn,historiqueDate,consoPeriode,submit);

        houseDetailView.setListener();

    }

    @Override
    public void initializeMvc() {
        houseDetailModel =new HouseDetailModel(houseId);
        houseDetailController= new HouseDetailController(houseDetailModel);
        houseDetailView=(HouseDetailView)houseDetailController.getView();
    }

    public static Context getlContext() {
        return lContext;
    }

}
