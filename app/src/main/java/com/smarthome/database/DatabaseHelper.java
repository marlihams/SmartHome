package com.smarthome.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smarthome.beans.Bean;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.beans.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
    OMRLite handler
     ************************************************/

    private static final String DATABASE_NAME = "smarthome.db";
    private static final int DATABASE_VERSION = 1;

    private Map<Class, Dao<Bean, Object>> beanDao = new HashMap<Class, Dao<Bean, Object>>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // R.raw.ormlite_config
    }

    /************************************************
     * methode to create all the table
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, House.class);
            TableUtils.createTable(connectionSource, Device.class);
            TableUtils.createTable(connectionSource, Historique.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {

            // In case of change in database of next version of application, it will be invoked
            //automatically.this has to be change
            // TODO  copy table from the old version
//            try {
//                List<String> allSql = new ArrayList<String>();
//                switch (oldVersion) {
//                    case 1:
//
//                }
//
//                for (String sql : allSql) {
//                    db.execSQL(sql);
//                }
//            } catch (android.database.SQLException e) {
//                Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
//                throw new RuntimeException(e);
//            }

            onCreate(sqliteDatabase, connectionSource);
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs


    public <T> Dao<T,Object> getBeanDao(Class<T> entityClass){
        Dao<Bean,Object> dao=null;
        if (beanDao.get(entityClass) == null) {
            try {
                dao = (Dao<Bean, Object>) getDao(entityClass);
            } catch (SQLException e) {
                Log.e(DatabaseHelper.class.getName(), "exception during the method getBeanDao", e);
                throw new RuntimeException(e);
            }
            beanDao.put(entityClass,dao);
        }

        return (Dao<T,Object>)beanDao.get(entityClass);
    }

    public void  emptyTable(){
        try {
            TableUtils.clearTable(getConnectionSource(),Historique.class);

            TableUtils.clearTable(getConnectionSource(),Device.class);
            TableUtils.clearTable(getConnectionSource(),House.class);
            TableUtils.clearTable(getConnectionSource(),User.class);

        } catch (SQLException e) {
            Log.e("ERROR","empy all table failed");
            e.printStackTrace();
        }


    }

    public void removeAll() {

        try {
            TableUtils.dropTable(getConnectionSource(), Historique.class, true);
            TableUtils.dropTable(getConnectionSource(), House.class, true);
            TableUtils.dropTable(getConnectionSource(), Device.class, true);
            TableUtils.dropTable(getConnectionSource(), User.class, true);

        } catch (SQLException e) {
            Log.e("ERROR","remove all failed");
            e.printStackTrace();
        }

    }
    public void createTable() {

        try {
            TableUtils.createTable(getConnectionSource(), Historique.class);
            TableUtils.createTable(getConnectionSource(), House.class);
            TableUtils.createTable(getConnectionSource(), Device.class);
            TableUtils.createTable(getConnectionSource(), User.class);

        } catch (SQLException e) {
            Log.e("ERROR","create all failed");
            e.printStackTrace();
        }
    }

}

