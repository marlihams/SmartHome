package com.smarthome.controller;

import com.smarthome.beans.User;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface UserControllerI {

    void connect (User user);
    void updateHouse();
    void createNewUser(User user);
    void disconnect();
}
