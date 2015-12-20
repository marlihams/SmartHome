package com.smarthome.controller;

import com.smarthome.beans.User;
import com.smarthome.model.UserModelI;
import com.smarthome.view.LoginView;
import com.smarthome.view.UserView;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserController implements UserControllerI {

    UserModelI usermodel;
    LoginView loginView;
    UserView userView;

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

    public UserController(UserModelI usermodel) {
        this.usermodel = usermodel;
        this.loginView=new LoginView(usermodel,this);
        this.userView=new UserView(usermodel,this);
    }

    @Override
    public void connect(User user) {
        User usercache=usermodel.connect(user);
        if (usercache !=null)
            loginView.displayAcceuilView();
        else
            loginView.displayError();
    }

    @Override
    public void updateHouse() {


    }

    @Override
    public void createNewUser(User user) {


    }

    @Override
    public void disconnect() {

    }
}
