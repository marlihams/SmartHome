package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.Historique;

/**
 * Created by Mdiallo on 22/12/2015.
 */
public class HistoriqueDao extends BaseDao<Historique> {

    private static HistoriqueDao historiqueDeviceDao;

    public static HistoriqueDao getInstance(Context ctx) {
        if (historiqueDeviceDao == null) {
            historiqueDeviceDao = new HistoriqueDao(ctx);
        }
        return historiqueDeviceDao;
    }

    private HistoriqueDao(Context ctx) {
        super();
        super.context = ctx;
    }
}