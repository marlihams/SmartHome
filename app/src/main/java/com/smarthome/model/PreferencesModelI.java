package com.smarthome.model;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface PreferencesModelI {

    public String findElement(String key);
    public boolean addElement(String key, String value);
    public  boolean removeElement(String key);

    
}
