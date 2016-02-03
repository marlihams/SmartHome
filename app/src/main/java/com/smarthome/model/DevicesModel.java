package com.smarthome.model;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.android.DevicesActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.House;
import com.smarthome.electronic.Const;
import com.smarthome.electronic.DeviceConnector;
import com.smarthome.view.DeviceObserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public class DevicesModel implements  DevicesModelI{

    // Debugging
    private static final String TAG = "DevicesModel";
    private static final boolean D = true;

    private  List<Device> devices;
    private DeviceCacheDao deviceCacheDao;
    private HouseCacheDao houseCacheDao;
    private List<DeviceObserver> deviceObservers=new ArrayList<DeviceObserver>();


    private DeviceListAdapter deviceListAdapter;
    private House house;

    private DeviceConnector connector;

    private String readMessage = null;
    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Const.MESSAGE_READ:
                    readMessage = (String) msg.obj;
                    Log.d(TAG, "Message read from inputstream:" + readMessage);
                    stopConnection();
                    break;

                case Const.MESSAGE_TOAST:
                    final String message = (String) msg.obj;
                    if(message != null) {
                        Log.d(TAG, Const.TOAST);
                    }
                    break;
            }
        }

    };
    
    @Override
    public void createNewDevice(int positionPiece, String name, String address) throws Exception{
        Device device=new Device(name,deviceListAdapter.getPieceName(positionPiece),house,address);
        deviceCacheDao.createOrUpdate(device);
        deviceListAdapter.addItem(device.getPieceName(),device.getName(),getEtatDevice(device));
        devices.add(device);
        notifyDevicesObserver();
    }


    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public DevicesModel(int houseId) throws Exception{
        deviceCacheDao=new DeviceCacheDao(DevicesActivity.getlContext());
        houseCacheDao=new HouseCacheDao(DevicesActivity.getlContext());
        house=houseCacheDao.findByPkey(houseId);
        devices=deviceCacheDao.findAllByForeignKey(houseId, "home_id");
        devices.get(0).setAdress("20:14:08:05:43:96");
        deviceListAdapter=null;
//        public DeviceListAdapter(Context context, List<String> listPieces,HashMap<String, List<Boolean>>  listSwitch,
//                HashMap<String, List<String>> listDevices) {
        initializeAdapter();

    }

    private void initializeAdapter() throws Exception{

        List<String>pieceName=buildPieces();

        HashMap<String,List<Boolean>> deviceEtat=buildDeviceEtat(pieceName);
        HashMap<String,List<String>> listdevicesName=buildDevicesName(pieceName);


        deviceListAdapter=new DeviceListAdapter(DevicesActivity.getlContext(),pieceName,deviceEtat,listdevicesName);
        deviceListAdapter.setDevicesModel(this);


    }

    private HashMap<String, List<String>> buildDevicesName(List<String>pieceName) {

        HashMap<String,List<String>> deviceName=new HashMap<>();
        for (int i=0,len=pieceName.size();i<len;i++){
            deviceName.put(pieceName.get(i),new ArrayList<String>());
        }
        String key;
        List<String> list;
        for (int i=0,len=devices.size();i<len;i++){
            key=devices.get(i).getPieceName();
            list=deviceName.get(key);
            list.add(devices.get(i).getName());
            deviceName.put(key,list);
        }

        return deviceName;

    }

    private HashMap<String, List<Boolean>> buildDeviceEtat(List<String>pieceName) throws Exception{

        ProgressDialog pb=ProgressDialog.show(DevicesActivity.getlContext(),"please wait...","contacting device via blutooth...",true);
        pb.setCancelable(true);
        HashMap<String,List<Boolean>> deviceEtat=new HashMap<>();
        for (int i=0,len=pieceName.size();i<len;i++){
            deviceEtat.put(pieceName.get(i),new ArrayList<Boolean>());
        }
        String deviceName;
        List<Boolean> etat;
        for (int i=0,len=devices.size();i<len;i++){
            deviceName=devices.get(i).getPieceName();
             etat=deviceEtat.get(deviceName);
                etat.add(getEtatDevice(devices.get(i)));
            deviceEtat.put(deviceName,etat);
        }
        pb.dismiss();
            return deviceEtat;

    }



    private Boolean getEtatDevice(Device device) throws Exception{

        if(device.getAdress() != null) {
            if(connector == null) {
                BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAdress());
                setupConnector(bluetoothDevice, mHandler);
            }
            try {
                long beginningTime = System.currentTimeMillis();
                while(true) {
                    if(isConnected()) {
                        connector.write("e".getBytes());
                        //connector.stopConnectThread();
                        break;
                    }
                    if(System.currentTimeMillis() - beginningTime > Const.CONNECTION_WAITING_TIME) {
                        throw new Exception("Unable to connect to the device");
                    }
                }
                beginningTime = System.currentTimeMillis();
                while(readMessage == null) {
                    if(System.currentTimeMillis() - beginningTime > Const.DATA_RECEIVING_WAITING_TIME) {
                        throw new Exception("Timeout: unable to receive data from device " + device.getAdress());
                    }
                }
                if("1".equals(readMessage)){
                    return true;
                } else if("0".equals(readMessage)) {
                    return false;
                } else {
                    throw new Exception("Unknown character " + readMessage + " received from device " + device.getAdress());
                }
            } catch (Exception e) {
                stopConnection();
                throw new Exception(e);
            } finally {
                if(readMessage != null) {
                    readMessage = null;
                }
            }
        } else {
            return false;
        }
    }


    private List<String> buildPieces() {
        List<String> pieceName=new ArrayList<String>();
        String n;
        for (int i=0,len=devices.size();i<len;i++){
            n=devices.get(i).getPieceName();
            if (!pieceName.contains(n))
                pieceName.add(n);
        }
        return pieceName;
    }

    @Override
    public void notifySwitchObserver(int parent , int child,boolean ischecked) throws Exception{
        for (int i=0;i<deviceObservers.size();i++){
            deviceObservers.get(i).updateDeviceLightObserver(parent, child, ischecked);
        }
    }

    @Override
    public void subscribeDeviceObserver(DeviceObserver observer) {
        deviceObservers.add(observer);

    }

    @Override
    public void notifyDevicesObserver() {
        // TODO notify the expandable list when needeed
        for (int i=0;i<deviceObservers.size();i++){
          deviceObservers.get(i).updateDeviceObserver();
        }

    }


    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public DeviceListAdapter getDeviceListAdapter() {
        return deviceListAdapter;
    }

    @Override
    public void deleteDevice(int piecePosition, int devicePosition) {
        if (devicePosition==-1) {
            // deleting a piece so we delette all the device having inside that pieceName;
            deviceCacheDao.delete(findDeviceAdapterByPieceName(piecePosition));
            deviceListAdapter.removeItem(deviceListAdapter.getPieceName(piecePosition));

        }
        else {
            deviceCacheDao.delete(findDeviceIdAdapter(piecePosition, devicePosition));
            deviceListAdapter.removeItem(deviceListAdapter.getPieceName(piecePosition), devicePosition);

        }
        devices=deviceCacheDao.findAllByForeignKey(house.getId(),"home_id");

    }

    @Override
    public void updateAdapter(Device device) throws Exception{

        deviceListAdapter.addItem(device.getPieceName(),device.getName(),getEtatDevice(device));}

    @Override
    public void updateDevice() {

    }
    public Device findDeviceIdAdapter(int parentPosition,int childPosition){

        String p=deviceListAdapter.getPieceName(parentPosition);
        String name=deviceListAdapter.getDeviceName(p,childPosition);
        boolean find=true;
        int id=0;
        int position=-1;
        for (int i=0,len=devices.size();i<len && find;i++){

            if (devices.get(i).getName().equals(name) && devices.get(i).getPieceName().equals(p)){
                find=false;
               // id=devices.get(i).getId();
                position=i;
            }
        }
        return position!=-1 ? devices.get(position):null;
    }
    public List<Device> findDeviceAdapterByPieceName(int parentPosition){

        String p=deviceListAdapter.getPieceName(parentPosition);
        boolean find=true;
        List<Device>rep=new ArrayList<>();
        int id=0;
        int position=-1;
        for (int i=0,len=devices.size();i<len && find;i++){

            if (devices.get(i).getPieceName().equals(p)){
                find=false;
               rep.add(devices.get(i));
            }
        }
        return rep;
    }



    @Override
    public DeviceConnector getConnector() {
        return connector;
    }


    @Override
    public void setupConnector(BluetoothDevice connectedDevice, Handler mHandler) {
        stopConnection();
        try {
            connector = new DeviceConnector(connectedDevice, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "setupConnector failed: " + e.getMessage());
        }
    }

    @Override
    public void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }

    @Override
    public boolean isConnected() {
        return (connector != null) && (connector.getState() == DeviceConnector.STATE_CONNECTED);
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
}
