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

    /*
    @Override
    public List<Historique> findAllByForeignKey(Historique obj,String foreignKey){

        try {
            if(obj.getHouse() != null) {
                QueryBuilder<Historique,Object> qb = getConnection().queryBuilder();
                Where where = qb.where();
                where.or(
                        where.eq("house", obj.getHouse()),
                        where.eq("device",obj.getHouse())
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return null;
    }
    */

}