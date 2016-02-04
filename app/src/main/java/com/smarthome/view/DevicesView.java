package com.smarthome.view;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.smarthome.R;
import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.Device;
import com.smarthome.controller.DevicesControllerI;
import com.smarthome.electronic.Const;
import com.smarthome.model.DevicesModelI;
import com.smarthome.vo.BluetoothVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class DevicesView extends BluetoothUtils implements SmartView,DeviceObserver{

    // Debugging
    private static final String TAG = "DevicesView";
    private static final boolean D = true;

    /*private String readMessage = null;
    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Const.MESSAGE_READ:
                    readMessage = (String) msg.obj;
                    if(D) Log.d(TAG, "Message read from inputstream:" + readMessage);
                    stopConnection();
                    break;

                case Const.MESSAGE_TOAST:
                    final String message = (String) msg.obj;
                    if(message != null) {
                        if(D) Log.d(TAG, Const.TOAST);
                    }
                    break;
            }
        }

    };*/

    public static  final String SELECTEDDEVICE="deviceId";

    private int selectedElement=-1;
    private ExpandableListView expandableListeDevices;
    private DevicesModelI devicesModel;
    private DevicesControllerI devicesController;
    private ImageButton add;
    private ImageButton delete;
    private  ImageButton scanDevice;
    BluetoothAdapter bdAdapter;
    private int piecePosition=-1;
    private int devicePosition=-1;
    private View oldView;
    private List<String>addressMacDevice;
    SimpleAdapter mdapter;
    private List<HashMap<String,String>>listeDevices;

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Create a new device item
                BluetoothVO bluetoothVO=new BluetoothVO(device.getName(),device.getAddress());
                listeDevices.add(bluetoothVO.getHashMap());
                mdapter.notifyDataSetChanged();
            }
        }
    };

    public DevicesView(DevicesControllerI devicesController,DevicesModelI devicesModel) {
        this.devicesController = devicesController;
        this.devicesModel = devicesModel;
        listeDevices=new ArrayList<HashMap<String,String>>();
         mdapter=new SimpleAdapter(DevicesActivity.getlContext(),listeDevices,android.R.layout.simple_list_item_2,
                new String[]{BluetoothVO.KeyNAME,BluetoothVO.KeyADRESS},new int[]{android.R.id.text1,android.R.id.text2});
        addressMacDevice=new ArrayList<>();
        initialize();
        subscribeObserver();

    }

    private void initialize() {
       addressMacDevice.clear();
        for (Device device : devicesController.getDevicesModel().getDevices())
            if (device.getAdress()!=null)
                addressMacDevice.add(device.getAdress());
    }

    @Override
    public void initializeWidget(View... views) {
        expandableListeDevices=(ExpandableListView)views[0];
        add=(ImageButton)views[1];
        delete=(ImageButton) views[2];
        scanDevice=(ImageButton)views[3];
        refreshView();

    }

    @Override
    public void setListener() {
        SmartAnimation.init(DevicesActivity.getlContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SmartChangeView.changeView(PiecesActivity.getlContext(),"profil");
                v.startAnimation(SmartAnimation.fad_in);
                dialogueAddPiece();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.fad_in);

                try {
                    devicesController.deleteDevice(piecePosition, devicePosition);
                } catch (Exception e) {
                    Toast.makeText(DevicesActivity.getlContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        expandableListeDevices.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                piecePosition = groupPosition;
                devicePosition = childPosition;

                changeView(); // display the detail of the device in other activity


                return false;

            }
        });
        expandableListeDevices.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (oldView != null)
                    oldView.setBackgroundResource(R.color.light_theme);
                if (selectedElement == groupPosition) {
                    v.setBackgroundResource(R.color.light_theme);
                    selectedElement = -1;
                } else {
                    v.setBackgroundColor(Color.LTGRAY);
                    selectedElement = groupPosition;
                }
                oldView = v;
                return false;
            }
        });
        expandableListeDevices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int indiceDevice = -2;
                int indicePiece = -2;
                int color = 0;

                int itemType = ExpandableListView.getPackedPositionType(id);
                view.setAnimation(SmartAnimation.fad_in);
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    indiceDevice = ExpandableListView.getPackedPositionChild(id);
                    indicePiece = ExpandableListView.getPackedPositionGroup(id);

                    color = R.color.light_red;

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    indicePiece = ExpandableListView.getPackedPositionGroup(id);
                    indiceDevice = -1;
                    color = R.color.light_theme;
                } else {
                    // nothing
                }

                if (oldView != null)
                    oldView.setBackgroundResource(color);

                if (indiceDevice == devicePosition && indicePiece == piecePosition) {
                    view.setBackgroundResource(color);
                } else {
                    devicePosition = indiceDevice;
                    piecePosition = indicePiece;
                    oldView = view;
                    view.setBackgroundResource(R.color.light_blue);
                }

                view.startAnimation(SmartAnimation.shake);

                return true;
            }
        });

        scanDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.hyperspace_out);
                bdAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bdAdapter.isEnabled() && selectedElement != -1) {
                    //  listeDevices.clear();
                    DevicesActivity.getlContext().registerReceiver(bReciever, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                    bdAdapter.startDiscovery();
                    try {
                        displayDialogueScan();
                    } catch (Exception e) {
                        Toast.makeText(DevicesActivity.getlContext(), "activate the bluetooth and selected pieceName", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(DevicesActivity.getlContext(), "activate the bluetooth and selected pieceName", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void dialogueAddPiece() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DevicesActivity.getlContext());
        alertDialog.setTitle("add a New Piece");
        alertDialog.setMessage("Enter  piece name");
        final EditText input = new EditText(DevicesActivity.getlContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String piece = input.getText().toString();
                        if (!piece.isEmpty()) {
                            devicesController.getDevicesModel().getDeviceListAdapter().addItem(piece);
                            dialog.dismiss();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.create();
        alertDialog.show();
    }

    private void displayDialogueScan() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(DevicesActivity.getlContext());
        dialog.setTitle("list of unknow Device");

        // if button is clicked, close the custom dialog
        dialog.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        dialog.setAdapter(
                mdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> selected = listeDevices.get(which);
                        // adding a new device
                        if (!addressMacDevice.contains(selected.get(BluetoothVO.KeyADRESS))) {
                            try {
                                devicesController.createNewDevice(selectedElement, selected.get(BluetoothVO.KeyNAME),
                                        selected.get(BluetoothVO.KeyADRESS));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(DevicesActivity.getlContext(), "that device has already been added", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
     //   dialog.create();
        dialog.show();
    }
    private void refreshView() {
        try {
            for(Device device : devicesController.getDevicesModel().getDevices()) {
                boolean etat = getEtatDevice(device.getAdress());
                if(device.getAdress() != null) {
                   if(D) Log.i(TAG, "Etat du device " + device.getAdress() + " est " + device.isLight());
                }
                device.setLight(etat);
            }
            devicesController.getDevicesModel().initializeAdapter();
            expandableListeDevices.setAdapter(devicesController.getDevicesModel().getDeviceListAdapter());
        } catch (Exception e) {
            if(D) Log.e(TAG, e.getMessage());
            Toast.makeText(DevicesActivity.getlContext(),"No device has been found ",Toast.LENGTH_LONG).show();
        }
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
    public void updateDeviceLightObserver(int parent, int child,boolean ischecked) throws Exception {

        Device device = devicesController.getDevicesModel().findDeviceIdAdapter(parent, child);
        if(device.getAdress() == null) {
            throw new Exception("The device must have an adress");
        }
        if(getConnector() == null) {
            BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAdress());
            setupConnector(bluetoothDevice, mHandler);
        }
        try {
            long beginningTime = System.currentTimeMillis();
            while(true) {
                if(isConnected()) {
                    if(ischecked) {
                        getConnector().write("o".getBytes());
                    } else {
                        getConnector().write("f".getBytes());
                    }
                    break;
                }
                if(System.currentTimeMillis() - beginningTime > Const.CONNECTION_WAITING_TIME) {
                    throw new Exception("Unable to connect to the device");
                }
            }
        } catch (Exception e) {
            stopConnection();
            throw new Exception(e);
        }
        /*RouteurManager routeurManager = devicesController.getDevicesModel().getRouteurManager();
        Device device = devicesController.getDevicesModel().getDevices().get(0);
        routeurManager.connect(device.getAdress());
        if(ischecked) {
            routeurManager.sendData("ON");
        } else {
           routeurManager.sendData("OFF");
        }
        routeurManager.close(device.getAdress());
        String address="192.168.43.91";
        HandlerRouteur handlerRouteur=new HandlerRouteur(DevicesActivity.getlContext(),address);
        handlerRouteur.getListDevices();*/
        devicesController.getDevicesModel().getDeviceListAdapter().updateState(parent, child);
    }




    private Boolean getEtatDevice(String adress) throws Exception{

        /*if(adress != null) {
            if(devicesController.getDevicesModel().getConnector() == null) {
                BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(adress);
                devicesController.getDevicesModel().setupConnector(bluetoothDevice, mHandler);
            }
            try {
                long beginningTime = System.currentTimeMillis();
                while(true) {
                    if(devicesController.getDevicesModel().isConnected()) {
                        devicesController.getDevicesModel().getConnector().write("e".getBytes());
                        //connector.stopConnectThread();
                        break;
                    }
                    if(System.currentTimeMillis() - beginningTime > Const.CONNECTION_WAITING_TIME) {
                        throw new Exception("Unable to connect to the device");
                    }
                }
                beginningTime = System.currentTimeMillis();
                while(readMessage == null) {
                }
                if("1".equals(readMessage)){
                    return true;
                } else if("0".equals(readMessage)) {
                    return false;
                } else {
                    throw new Exception("Unknown character " + readMessage + " received from device " + adress);
                }
            } catch (Exception e) {
                devicesController.getDevicesModel().stopConnection();
                throw new Exception(e);
            } finally {
                if(readMessage != null) {
                    readMessage = null;
                }
            }
        } else {
            return false;
        }*/
        return false;
    }

}