package com.smarthome.view;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.smarthome.electronic.Const;
import com.smarthome.electronic.DeviceConnector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BluetoothUtils {

    // Debugging
    private static final String TAG = "BluetoothUtils";
    private static final boolean D = true;

    private DeviceConnector connector;

    private String readMessage = null;
    protected final Handler mHandler = new Handler() {
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

    };

    public String getReadMessage() {
        return readMessage;
    }

    public DeviceConnector getConnector() {
        return connector;
    }

    public void setupConnector(BluetoothDevice connectedDevice, Handler mHandler) {
        stopConnection();
        try {
            connector = new DeviceConnector(connectedDevice, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "setupConnector failed: " + e.getMessage());
        }
    }

    public void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }

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
