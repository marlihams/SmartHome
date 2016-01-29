package com.smarthome.electronic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class ElectronicManager {
    // le périphérique (le module bluetooth)
    private BluetoothDevice device = null;
    private BluetoothSocket socket = null;
    // Canal de réception
    private InputStream receiver = null;
    // Canal d'émission
    private OutputStream sender = null;
    private final String CONNECTION_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private ReceiverThread receiverThread;

    Handler handler;
    private Exception exception = null;

    public ElectronicManager(Handler hStatus, Handler h, String adress) throws Exception{
        // On récupère la liste des périphériques associés
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        // On parcourt la liste pour trouver notre module bluetooth
        for(BluetoothDevice device : pairedDevices) {
            // On teste si l'adresse de ce périphérique est le même que celui du module bluetooth connecté au microcontrôleur
            if(adress.equals(device.getAddress())) {
                try {
                    this.device = device;
                    // On récupère le socket de notre périphérique
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString(CONNECTION_UUID));
                    // Canal de réception (valide uniquement après la connexion)
                    receiver = socket.getInputStream();
                    // Canal d'émission (valide uniquement après la connexion)
                    sender = socket.getOutputStream();
                    handler = hStatus;
                    // On crée le thread de réception des données avec l'Handler venant du thread UI
                    receiverThread = new ReceiverThread(h);
                } catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
                break;
            }
        }
    }



    public BluetoothDevice getDevice() {
        return device;
    }

    public void sendData(String data) throws Exception{
        if(sender != null && data != null) {
            try {
                // On écrit les données dans le buffer d'envoi
                sender.write(data.getBytes());
                // On s'assure qu'elles soient bien envoyées
                sender.flush();
            } catch (IOException e) {
                throw new Exception("sent failed");
            }
        }
    }

    public void connect()throws RuntimeException{
        Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

            }
        };
        Thread t = new Thread() {
            @Override public void run() {
                try {
                    // Tentative de connexion
                    socket.connect();
                    // Connexion réussie
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1;
                    handler.sendMessage(msg);
                    // On démarre la vérification des données
                    receiverThread.start();

                } catch (IOException e) {
                    // Echec de la connexion
                    exception = e;
                } finally {
                    try {
                        if(isConnected()) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        exception = e;
                    }
                }
                throw new RuntimeException("Connection Failed : " + exception.getMessage());
            }
        };
        t.setUncaughtExceptionHandler(exceptionHandler);
        t.start();
    }

    public void close() throws Exception {
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new Exception("Socket closing failed");
            }
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
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
                    // On teste si des données sont disponibles
                    if(receiver.available() > 0) {

                        byte buffer[] = new byte[100];
                        // On lit les données, k représente le nombre de bytes lu
                        int k = receiver.read(buffer, 0, 100);

                        if(k > 0) {
                            // On convertit les données en String
                            byte rawdata[] = new byte[k];
                            for(int i=0;i<k;i++) {
                                rawdata[i] = buffer[i];
                            }

                            String data = new String(rawdata);

                            // On envoie les données dans le thread de l'UI pour les afficher
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
