package com.smarthome.controller;

import com.smarthome.beans.House;
import com.smarthome.beans.User;

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
