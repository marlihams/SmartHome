package com.smarthome.controller;

import com.smarthome.beans.*;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface UserControllerI {

    void connect (House house,User user);
    public void updateHouse(User user);
    void createNewUser(User user);
    void disconnect();
    void addUser(User user);
    void testDatabase();

}
