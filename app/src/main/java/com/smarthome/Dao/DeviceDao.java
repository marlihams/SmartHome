package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.Device;

/**
 * Created by Mdiallo on 22/12/2015.
 */
public class DeviceDao extends BaseDao<Device> {

private static DeviceDao deviceDao;

public static DeviceDao getInstance(Context ctx) {
        if (deviceDao == null) {
        deviceDao = new DeviceDao(ctx);
        }
        return deviceDao;
        }

private DeviceDao(Context ctx) {
        super();
        super.context = ctx;
        }
}