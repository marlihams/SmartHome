package com.smarthome.model;

import com.smarthome.beans.User;
import com.smarthome.database.CacheManager;
import com.smarthome.database.CacheManagerI;
import com.smarthome.database.DatabaseManager;
import com.smarthome.database.DatabaseManagerI;
import com.smarthome.view.UserObsever;

import java.util.ArrayList;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserModel implements UserModelI {

    private ArrayList<UserObsever> userobservers=new ArrayList<UserObsever>();
    private DatabaseManagerI databaseManager;
    private CacheManagerI cacheManager;

    public UserModel() {
        cacheManager=new CacheManager();
        databaseManager=new DatabaseManager();
    }

    public DatabaseManagerI getDatabaseManager() {
        return databaseManager;
    }

    public CacheManagerI getCacheManager() {
        return cacheManager;
    }

    @Override
    public void subscribeObserver(UserObsever observer) {
        userobservers.add(observer);
    }

    @Override
    public void removeObserver(UserObsever observer) {
        int index=userobservers.indexOf(observer);
        if (index >=0)
            userobservers.remove(index);
    }

    @Override
    public User connect(User user) {
       User userCache= cacheManager.getUser(user);
        if (userCache !=null &&  userCache.getPassword().equals(user.getPassword())){ //check on cache
            return userCache;
        }
        else{ // check on database
           userCache=databaseManager.getUser(user);
            if (userCache !=null)
                cacheManager.setUser(userCache);
        }
            return userCache;

    }

    @Override
    public void notifyObserver() {
        int len=userobservers.size();
        for (int i=0;i<len;i++)
            userobservers.get(i).updateUserInfo();
    }
}
