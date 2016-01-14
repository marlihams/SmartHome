package com.smarthome.electronic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class ElectronicManager {
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private InputStream receiver;
    private OutputStream sender;
    private final String CONNECTION_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private ReceiverThread receiverThread;

    Handler handler;

    public ElectronicManager(Handler hStatus, Handler h, String adress) {
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        for(BluetoothDevice device : pairedDevices) {
            if(device.getAddress().equals(adress)) {
                try {
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString(CONNECTION_UUID));
                    receiver = socket.getInputStream();
                    sender = socket.getOutputStream();
                    this.device = device;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        handler = hStatus;

        receiverThread = new ReceiverThread(h);
    }



    public BluetoothDevice getDevice() {
        return device;
    }

    public void sendData(String data) {
        try {
            sender.write(data.getBytes());
            sender.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        new Thread() {
            @Override public void run() {
                try {
                    socket.connect();

                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1;
                    handler.sendMessage(msg);

                    receiverThread.start();

                } catch (IOException e) {
                    Log.v("N", "Connection Failed : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReceiverThread extends Thread {
        Handler handler;

        ReceiverThread(Handler h) {
            handler = h;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    if(receiver.available() > 0) {

                        byte buffer[] = new byte[100];
                        int k = receiver.read(buffer, 0, 100);

                        if(k > 0) {
                            byte rawdata[] = new byte[k];
                            for(int i=0;i<k;i++)
                                rawdata[i] = buffer[i];

                            String data = new String(rawdata);

                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putString("receivedData", data);
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
