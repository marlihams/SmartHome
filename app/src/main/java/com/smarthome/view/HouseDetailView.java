package com.smarthome.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.android.HouseDetailActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.controller.HouseDetailControllerI;
import com.smarthome.controller.HousesControllerI;
import com.smarthome.model.HouseDetailModelI;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class HouseDetailView implements SmartView,HouseObserver {

    public static String SELECTEDHOUSE="selectedHouse";
    private ListView listeHouses;
    private HouseDetailControllerI houseDetailController;
    private HouseDetailModelI houseDetailModel;
    private List<String>consommation;
    private EditText houseName;
    private  EditText houseAddress;
    private TextView nbDevice;
    private TextView nbTurnOn;
    private  TextView nbTurnOff;
    private  TextView nbBroke;
    private Spinner historiqueDate;
    private TextView  consoPeriode;
    private ImageButton submit;

    public HouseDetailView(HouseDetailControllerI houseDetailController,HouseDetailModelI houseDetailModel) {
        this.houseDetailController = houseDetailController;
        this.houseDetailModel=houseDetailModel;
        consommation=new ArrayList<String>();
        subscribeObserver();
    }


    @Override
    public void initializeWidget(View... views) {
        houseName=(EditText)views[0];
        houseAddress=(EditText)views[1];
        nbDevice=(TextView)views[2];
        nbBroke=(TextView)views[3];
        nbTurnOff=(TextView)views[4];
        nbTurnOn=(TextView)views[5];
        historiqueDate=(Spinner)views[6];
        consoPeriode=(TextView)views[7];
        submit=(ImageButton)views[8];
        displayWidgetContent();
    }



    @Override
    public void setListener() {

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            House house = houseDetailController.getHouseDetailModel().getHouse();
            String name = houseName.getText().toString();
            String address = houseAddress.getText().toString();
            if (name.equals(house.getName()) && address.equals(house.getAddress())) {
                Toast.makeText(HouseDetailActivity.getlContext(), "nothing to update", Toast.LENGTH_SHORT).show();
            } else {
                houseDetailController.getHouseDetailModel().updateHouse(name, address);
            }
        }
    });
        historiqueDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consoPeriode.setText(addSymbole(consommation.get(position)));
            }
        });
    }


    private  void displayWidgetContent(){
     nbDevice.setText(houseDetailController.getHouseDetailModel().getDevices().size()+"");
        Map houseDetail=houseDetailController.getHouseDetailModel().getHouseDetail();

        nbBroke.setText(houseDetail.get("broke")+"");
        nbTurnOff.setText(houseDetail.get("turnoff")+"");
        nbTurnOn.setText(houseDetail.get("turnon")+"");
        updateSpinner(); // fill spinner and select the first element
        consoPeriode.setText(addSymbole(consommation.get(0)));
    }

    @Override
    public void subscribeObserver() {
    // no a subscriber
        houseDetailModel.subscribeHouseObserver((HouseObserver) this);
    }

    @Override
    public void updateHouseObserver() {
       House house= houseDetailController.getHouseDetailModel().getHouse();
        houseAddress.setText(house.getAddress());
        houseName.setText(house.getName());
    }

    private void  updateSpinner(){

        List<String>houseHistorique=new ArrayList<String>();
        List<Historique> historique=houseDetailController.getHouseDetailModel().getHouseHistorique();
        for (int i=0,len=historique.size();i<len;i++){
            houseHistorique.add(historique.get(i).getDateDebut()+" : "+historique.get(i).getDateFin());
            consommation.add(historique.get(i).getHouseConso().getConsomation());
        }

        ArrayAdapter<String> adapter= new ArrayAdapter(HouseDetailActivity.getlContext(),android.R.layout.simple_list_item_1,houseHistorique);
        historiqueDate.setAdapter(adapter);
        historiqueDate.setSelection(0);
    }
    private  String addSymbole(String a){
        return a+ " "+ Currency.getInstance(Locale.getDefault()).getSymbol();
    }
}
