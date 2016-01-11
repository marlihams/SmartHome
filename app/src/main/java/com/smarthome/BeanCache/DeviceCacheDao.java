package com.smarthome.BeanCache;

import android.content.Context;

import com.smarthome.beans.Device;

/**
 * Created by Mdiallo on 25/12/2015.
 */
public class DeviceCacheDao extends CacheDao<Device> {

    public DeviceCacheDao(Context ctx) {
        super(ctx);
    }
}
