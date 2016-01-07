package com.smarthome.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;


import com.smarthome.controller.DevicesControllerI;
import com.smarthome.model.DevicesModelI;

import java.util.List;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class DevicesView implements SmartView,DeviceObserver {


    private ExpandableListView expandableListeDevices;
    private DevicesModelI devicesModel;
    private DevicesControllerI devicesController;
    private ImageButton addPiece;
    private ImageButton deletePiece;

    public DevicesView(DevicesControllerI devicesController,DevicesModelI deviceModel) {
        this.devicesController = devicesController;
        this.devicesModel = devicesModel;
        subscribeObserver();

    }


    @Override
    public void initializeWidget(View... views) {
        expandableListeDevices=(ExpandableListView)views[0];
        addPiece=(ImageButton)views[1];
        deletePiece=(ImageButton) views[2];
        refreshView();

    }

    @Override
    public void setListener() {

        addPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SmartChangeView.changeView(PiecesActivity.getlContext(),"profil");
            }
        });
        deletePiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void refreshView() {

    }

    @Override
    public void subscribeObserver() {
     this.devicesModel.subscribeDeviceObserver((DeviceObserver)this);
    }

    public  void changeView(){

//        if (position>=0){
//
//            Piece house=devicesController.getPiecesModel().getPieces().get(position);
//            Context ctx=PiecesActivity.getlContext();
//            Intent intent=new Intent(ctx, PieceDetailActivity.class);
//            intent.putExtra(SELECTEDHOUSE,position);
//            ctx.startActivity(intent);


    }

    @Override
    public void updateDevice() {

    }
}
