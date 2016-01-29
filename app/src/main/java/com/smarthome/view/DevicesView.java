package com.smarthome.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.Device;
import com.smarthome.controller.DevicesControllerI;
import com.smarthome.electronic.DeviceConnector;
import com.smarthome.model.DevicesModelI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class DevicesView implements SmartView,DeviceObserver {

    // Debugging
    private static final String TAG = "DevicesView";
    private static final boolean D = true;

    public static  final String SELECTEDDEVICE="deviceId";
    // The amount of time untill cancelling connection request (in milliseconds)
    private static final long CONNECTION_WAITING_TIME = 10000;
    private ExpandableListView expandableListeDevices;
    private DevicesModelI devicesModel;
    private DevicesControllerI devicesController;
    private ImageButton addPiece;
    private ImageButton deletePiece;
    private int piecePosition=-1;
    private int devicePosition=-1;

    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the DeviceConnector handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    private static String MSG_NOT_CONNECTED ="not connected";
    private static String MSG_CONNECTING = "connecting";
    private static String MSG_CONNECTED = "connected";

    // Name of the connected device
    private String connectedDeviceName = null;

    private static DeviceConnector connector;

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
    public void updateDeviceLightObserver(int parent, int child,boolean ischecked) throws Exception {

        Device device = devicesController.getDevicesModel().findDeviceIdAdapter(parent, child);
        if(device.getAdress() == null) {
            throw new Exception("The device must have an adress");
        }
        BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAdress());
        if(connector == null) {
            setupConnector(bluetoothDevice);
        }
        try {
            long beginningTime = System.currentTimeMillis();
            while(true) {
                if(isConnected()) {
                    if(ischecked) {
                        connector.write("o".getBytes());
                    } else {
                        connector.write("f".getBytes());
                    }
                    break;
                }
                if(System.currentTimeMillis() - beginningTime > CONNECTION_WAITING_TIME) {
                    throw new Exception("Unable to connect to the device");
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            stopConnection();
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

    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:

                        Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                        switch (msg.arg1) {
                            case DeviceConnector.STATE_CONNECTED:
                                Log.d(TAG, MSG_CONNECTED);
                                break;
                            case DeviceConnector.STATE_CONNECTING:
                                Log.d(TAG, MSG_CONNECTING);
                                break;
                            case DeviceConnector.STATE_NONE:
                                Log.d(TAG, MSG_NOT_CONNECTED);
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        final String readMessage = (String) msg.obj;
                        if (readMessage != null) {
                            Log.d(TAG, "MESSAGE READ " + readMessage);
                        }
                        break;

                    case MESSAGE_DEVICE_NAME:
                        final String deviceName = (String) msg.obj;
                        if (deviceName != null) {
                            Log.d(TAG, DEVICE_NAME + " " + deviceName);
                        }
                        break;

                    case MESSAGE_WRITE:
                        // stub
                        break;

                    case MESSAGE_TOAST:
                        final String message = (String) msg.obj;
                        if(message != null) {
                            Log.d(TAG, TOAST);
                        }
                        break;
                    default:
                        Log.d(TAG, "what:" + msg.what + " arg1:" + msg.arg1 + " obj:" + (String)msg.obj);
                        break;
                }
            }

    };

    private void setupConnector(BluetoothDevice connectedDevice) {
        stopConnection();
        try {
            connector = new DeviceConnector(connectedDevice, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "setupConnector failed: " + e.getMessage());
        }
    }

    private void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }

    public static BluetoothSocket createRfcommSocket(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        try {
            Class class1 = device.getClass();
            Class aclass[] = new Class[1];
            aclass[0] = Integer.TYPE;
            Method method = class1.getMethod("createRfcommSocket", aclass);
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(1);

            tmp = (BluetoothSocket) method.invoke(device, aobj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            if (D) Log.e(TAG, "createRfcommSocket() failed", e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            if (D) Log.e(TAG, "createRfcommSocket() failed", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            if (D) Log.e(TAG, "createRfcommSocket() failed", e);
        }
        return tmp;
    }

    private boolean isConnected() {
        return (connector != null) && (connector.getState() == DeviceConnector.STATE_CONNECTED);
    }
}
