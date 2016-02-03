package com.smarthome.electronic;

/**
 * Created by Amstrong on 31/1/2016.
 */
public class Const {
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_READ = 1;
    public static final int MESSAGE_TOAST = 2;

    // Key names received from the DeviceConnector handler
    public static final String TOAST = "Toast";

    // The amount of time untill cancelling connection request (in milliseconds)
    public static final long CONNECTION_WAITING_TIME = 10000;
    // The amount of time untill cancelling waiting thread (in milliseconds)
    public static final long DATA_RECEIVING_WAITING_TIME = 20000;
}
