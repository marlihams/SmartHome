package com.smarthome.controller;

import android.util.Log;

import com.smarthome.Dao.UserDao;
import com.smarthome.beans.*;
import com.smarthome.model.UserModelI;
import com.smarthome.view.LoginView;
import com.smarthome.view.UserView;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserController implements UserControllerI {

    UserModelI userModel;
    LoginView loginView;
    UserView userView;

    @Override
    public void addUser(User user) {

    }


    @Override
    public void testDatabase() {

     //   User user=new User("mohamedmadioud@yahoo.fr","smarthome");
      //  UserDao userDao=userModel.getUserDao();
      //  userDao.createOrUpdate(user);
    //   user= userModel.getUserByEmailAndPassword(user);


//        Log.i("INSERt","SUCCEDEED");
//        Log.i("USERID",""+user.getId());
    }

    public UserModelI getUserModel() {
        return userModel;
    }

    public void setUsermodel(UserModelI userModel) {
        this.userModel = userModel;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public UserController(UserModelI userModel) {
        this.userModel = userModel;
        this.loginView=new LoginView(userModel,this);
        this.userView=new UserView(userModel,this);
    }

    @Override
    public void connect(House house,User user) {
      //  User usercache=userModel.connect(user);
        if (house.getUser().getPassword().equals(user.getPassword()) && house.getUser().getEmail().equals(user.getEmail())){

            loginView.displayAcceuilView(house.getUser());
        }

    }

    @Override
    public void updateHouse(User user) {
     // List<House> houses= userModel.getAllHouses();
        userModel.updateHouse(user);
    }

    @Override
    public void createNewUser(User user) {

    }

    @Override
    public void disconnect() {

    }
}
