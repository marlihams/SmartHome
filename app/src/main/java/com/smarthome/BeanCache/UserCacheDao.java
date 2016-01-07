package com.smarthome.BeanCache;

import android.content.Context;

import com.smarthome.beans.User;

import java.util.List;

/**
 * Created by Mdiallo on 25/12/2015.
 */
public class UserCacheDao extends CacheDao<User> {

    public UserCacheDao(Context ctx) {
        super(ctx);
    }
    public User getUserFromCache(User user) {

        List<User> users = findAll();
        if (!users.isEmpty() && users.get(0).getEmail().equals(user.getEmail()) && users.get(0).getPassword().equals(user.getPassword()))
            return users.get(0);
        else return null;
    }


}
