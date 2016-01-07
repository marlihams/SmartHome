package com.smarthome.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.smarthome.beans.User;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DatabaseManager implements DatabaseManagerI {

    private DatabaseHelper databaseHelper=null;
    private static  DatabaseManager instance;

    public DatabaseManager(Context context) {
        if (databaseHelper == null) {
         //   databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
            databaseHelper =new DatabaseHelper(context);
        }
    }
    public static void init(Context ctx){
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }

    }

    public static  DatabaseManager getInstance() {
        return instance;
    }

    public DatabaseHelper getHelper(){

        return databaseHelper;

    }



    public void  DestroyHelper(){
        /*
		 * releasing the helper when done.
		 */

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
