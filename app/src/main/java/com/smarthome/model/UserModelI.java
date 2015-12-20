package com.smarthome.model;

import com.smarthome.beans.User;
import com.smarthome.view.UserObsever;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface UserModelI {
    public void subscribeObserver(UserObsever observer);
    public void notifyObserver();
    public void removeObserver(UserObsever observer);
    public User connect (User user);

}
