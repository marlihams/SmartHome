package com.smarthome.vo;

import java.util.HashMap;

/**
 * Created by Mdiallo on 16/01/2016.
 */
public class BluetoothVO {
    public static final  String KeyNAME="name";
    public static final String KeyADRESS="adress";
    private String Name;
    private String address;
    private HashMap<String,String> hashMap;

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public BluetoothVO(String name, String address) {
        Name = name;
        this.address = address;
        hashMap=new HashMap<String,String>();
        hashMap.put(KeyNAME,name);
        hashMap.put(KeyADRESS,address);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
