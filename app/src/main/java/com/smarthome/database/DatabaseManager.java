package com.smarthome.database;

import com.smarthome.beans.User;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DatabaseManager implements DatabaseManagerI {
    @Override
    public User getUser(User user ) {
        return user;
    }

    @Override
    public void setUser(User user) {

    }
}
