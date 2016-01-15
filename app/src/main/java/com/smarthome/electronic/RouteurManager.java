package com.smarthome.electronic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Amstrong on 12/1/2016.
 */
public class RouteurManager implements RouteurManagerI {

    private String routeurIpAdress = "192.168.43.91";

    private static String get(String url) throws IOException {
        String source ="";
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            source +=inputLine;
        in.close();
        return source;
    }

    public void connect(final String adress) {
        new Thread() {
            @Override public void run() {
                try {
                    get("http://" + routeurIpAdress + "pair.php?adressMac=" + adress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendData(String data) {
        try {
            get("http://" + routeurIpAdress +"/order.php?order="+data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(String adress) {
        try {
            get("http://" + routeurIpAdress + "unpair.php?adressMac=" + adress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
