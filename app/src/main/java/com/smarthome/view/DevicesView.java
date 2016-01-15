package com.smarthome.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.Device;
import com.smarthome.controller.DevicesControllerI;
import com.smarthome.electronic.ElectronicManager;
import com.smarthome.model.DevicesModelI;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class DevicesView implements SmartView,DeviceObserver {

    public static  final String SELECTEDDEVICE="deviceId";
    private ExpandableListView expandableListeDevices;
    private DevicesModelI devicesModel;
    private DevicesControllerI devicesController;
    private ImageButton addPiece;
    private ImageButton deletePiece;
    private int piecePosition=-1;
    private int devicePosition=-1;




    public DevicesView(DevicesControllerI devicesController,DevicesModelI devicesModel) {
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
        SmartAnimation.init(DevicesActivity.getlContext());
        addPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SmartChangeView.changeView(PiecesActivity.getlContext(),"profil");
            }
        });
        deletePiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.fad_in);
                int a=0;

            }
        });

        expandableListeDevices.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,int childPosition, long id) {
                piecePosition=groupPosition;
                devicePosition=childPosition;

                changeView(); // display the detail of the device in other activity


                return false;

            }
        });
        expandableListeDevices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);
                view.setAnimation(SmartAnimation.fad_in);
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    devicePosition = ExpandableListView.getPackedPositionChild(id);
                    piecePosition = ExpandableListView.getPackedPositionGroup(id);


                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    piecePosition = ExpandableListView.getPackedPositionGroup(id);
                    devicePosition = -1;
                } else {
                    // nothing
                }

                return false;
            }
        });
    }

    private void refreshView() {
        expandableListeDevices.setAdapter(devicesController.getDevicesModel().getDeviceListAdapter());

    }

    public  void changeView(){

        if (piecePosition!=-1 && devicePosition!=-1){
                Context ctx=DevicesActivity.getlContext();
                Intent intent=new Intent(DevicesActivity.getlContext(), DeviceDetailActivity.class);
              //  int deviceId=devicesController.getDevicesModel().findDeviceIdAdapter(piecePosition,devicePosition);
                intent.putExtra(SELECTEDDEVICE, devicesController.getDevicesModel().
                        findDeviceIdAdapter(piecePosition,devicePosition).getId());
                ctx.startActivity(intent);
        }
    }

    @Override
    public void subscribeObserver() {
     this.devicesModel.subscribeDeviceObserver((DeviceObserver) this);
    }

    @Override
    public void updateDeviceObserver() {

//        if (position>=0){
//
//            Piece house=devicesController.getPiecesModel().getPieces().get(position);
//            Context ctx=PiecesActivity.getlContext();
//            Intent intent=new Intent(ctx, PieceDetailActivity.class);
//            intent.putExtra(SELECTEDHOUSE,position);
//            ctx.startActivity(intent);


    }

    @Override
    public void updateDeviceLightObserver(int parent, int child,boolean ischecked) {

        Device device = devicesController.getDevicesModel().findDeviceIdAdapter(parent, child);
        ElectronicManager electronicManager = connectToDeviceByBluetooth(device);
        try { if(ischecked) {
            electronicManager.sendData("o");
        } else {
            electronicManager.sendData("f");
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            electronicManager.close();
        }
        /*RouteurManager routeurManager = devicesController.getDevicesModel().getRouteurManager();
        Device device = devicesController.getDevicesModel().getDevices().get(0);
        routeurManager.connect(device.getAdress());
        if(ischecked) {
            routeurManager.sendData("ON");
        } else {
           routeurManager.sendData("OFF");
        }
        routeurManager.close(device.getAdress());*/
        devicesController.getDevicesModel().getDeviceListAdapter().updateState(parent, child);
    }

    private ElectronicManager connectToDeviceByBluetooth(Device device) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("receivedData");
            }
        };

        final Handler handlerStatus = new Handler() {
            public void handleMessage(Message msg) {
            }
        };
        //00:15:83:0C:BF:EB
        return new ElectronicManager(handlerStatus,handler,device.getAdress());
    }

}
