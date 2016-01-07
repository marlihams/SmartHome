package com.smarthome.Dao;

import android.content.Context;

import com.smarthome.beans.Device;
import com.smarthome.beans.House;
import com.smarthome.beans.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public class UserDao extends BaseDao<User>{

    private static UserDao userDao;

    public static UserDao getInstance(Context ctx) {
        if(userDao == null) {
            userDao = new UserDao(ctx);
        }
        return userDao;
    }
    private UserDao(Context ctx){
        super();
        super.context=ctx;
    }

}
