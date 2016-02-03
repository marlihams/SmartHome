package com.smarthome.model;

import android.content.Context;

import com.smarthome.database.PreferenceManager;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class PreferencesModel implements PreferencesModelI {

    private PreferenceManager preferenceManager;

    public PreferencesModel(Context ctx) {
        this.preferenceManager =PreferenceManager.getInstance(ctx);
    }

    @Override
    public String findElement(String key) {
        return  preferenceManager.findElement(key);
    }

    @Override
    public boolean addElement(String key, String value) {
        return preferenceManager.addElement(key,value);
    }

    @Override
    public boolean removeElement(String key) {
        return preferenceManager.removeElement(key);
    }
}
