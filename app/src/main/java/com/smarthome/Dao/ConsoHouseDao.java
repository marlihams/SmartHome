package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.ConsoDevice;
import com.smarthome.beans.ConsoHouse;

/**
 * Created by Mdiallo on 22/12/2015.
 */
public class ConsoHouseDao extends BaseDao<ConsoHouse> {

    private static ConsoHouseDao consoHouseDao;

    public static ConsoHouseDao getInstance(Context ctx) {
        if (consoHouseDao == null) {
            consoHouseDao = new ConsoHouseDao(ctx);
        }
        return consoHouseDao;
    }

    private ConsoHouseDao(Context ctx) {
        super();
        super.context = ctx;
    }
}