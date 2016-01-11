package com.smarthome.model;

import com.smarthome.Dao.UserDao;
import com.smarthome.beans.House;
import com.smarthome.beans.User;
import com.smarthome.view.SpinnerObserver;
import com.smarthome.view.UserObsever;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface UserModelI {
    public void subscribeUserObserver(UserObsever observer);
    public void subscribeSpinnerObserver(SpinnerObserver observer);
    public void notifyUserObserver();
    public void notifySpinnerObserver();
    public void removeObserver(UserObsever observer);

    public UserDao getUserDao();
    public User getUserByEmailAndPassword(User user);
    List<House> getAllHouses();
    public  void updateHouse(User user);
  //  public List<House>findAllHouseByUser(User user); not use anymore

}
