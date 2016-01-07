package com.smarthome.view;

import android.view.View;

import com.smarthome.controller.UserControllerI;
import com.smarthome.model.UserModelI;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserView implements UserObsever,SmartView {

    UserModelI userModel;
    UserControllerI userController;

    public UserView(UserModelI userModel, UserControllerI userController) {
        this.userModel = userModel;
        this.userController = userController;
        subscribeObserver();
    }

    public void subscribeObserver(){
        this.userModel.subscribeUserObserver((UserObsever)this);
    }

    @Override
    public void updateUserInfo() {

    }

    @Override
    public void initializeWidget(View... views) {


    }

    @Override
    public void setListener() {


    }
}
