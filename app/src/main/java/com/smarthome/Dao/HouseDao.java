package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.House;

/**
 * Created by Mdiallo on 22/12/2015.
 */
public class HouseDao extends BaseDao<House> {

    private static HouseDao houseDao;

    public static HouseDao getInstance(Context ctx) {
        if (houseDao == null) {
            houseDao = new HouseDao(ctx);
        }
        return houseDao;
    }

    private HouseDao(Context ctx) {
        super();
        super.context = ctx;
    }

}