package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.ConsoDevice;

/**
 * Created by Mdiallo on 22/12/2015.
 */
public class ConsoDeviceDao extends BaseDao<ConsoDevice> {

    private static ConsoDeviceDao consoDeviceDao;

    public static ConsoDeviceDao getInstance(Context ctx) {
        if (consoDeviceDao == null) {
            consoDeviceDao = new ConsoDeviceDao(ctx);
        }
        return consoDeviceDao;
    }

    private ConsoDeviceDao(Context ctx) {
        super();
        super.context = ctx;
    }
}