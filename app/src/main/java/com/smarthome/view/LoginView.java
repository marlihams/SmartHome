package com.smarthome.view;

import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.LoginActivity;
import com.smarthome.beans.User;
import com.smarthome.controller.UserControllerI;
import com.smarthome.model.UserModelI;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class LoginView  implements SmartView {
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private View progressView;
    private ImageButton connexionView;
    private ImageButton  newAccountView;

    UserModelI usermodel;
    UserControllerI userController;

    public LoginView(UserModelI usermodel, UserControllerI userController) {
        this.usermodel = usermodel;
        this.userController = userController;

    }

    public UserControllerI getUserController() {
        return userController;
    }

    public void setUserController(UserControllerI userController) {
        this.userController = userController;
    }

    @Override
    public void initializeWidget(View... views) {
        emailView=(AutoCompleteTextView)views[0];
        passwordView=(EditText)views[1];
        progressView=views[2];
        connexionView=(ImageButton)views[3];
        newAccountView=(ImageButton)views[4];

    }

    @Override
    public void setListener() {
        connexionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.connect(new User(emailView.getText().toString(),passwordView.getText().toString()));
            }
        });
    }


    public UserModelI getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(UserModelI usermodel) {
        this.usermodel = usermodel;
    }

    public void displayAcceuilView(){
        SmartChangeView.changeView(LoginActivity.getlContext(),"acceuil");
    }

    public void displayError() {

    }



}