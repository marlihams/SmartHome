package com.smarthome.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.smarthome.R;
import com.smarthome.controller.UserController;
import com.smarthome.controller.UserControllerI;
import com.smarthome.model.UserModel;
import com.smarthome.model.UserModelI;
import com.smarthome.view.LoginView;
import com.smarthome.view.SmartHomeView;

public class LoginActivity extends Activity implements SmartHomeView {

    private LoginView loginview;
    private static Context lContext;


    // attribute from layout
    AutoCompleteTextView emailView;
    private EditText passwordView;
    private View mProgressView;
    private ImageButton connexionView;
    private ImageButton  newAccountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lContext=this;
        // creation controller view model
        initializeMvc();

        //getting object from their Ids
        emailView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);
        mProgressView=(View) findViewById(R.id.login_progress);
        connexionView=(ImageButton)findViewById(R.id.connexion);
        newAccountView=(ImageButton)findViewById(R.id.newacount);

        loginview.initializeWidget(emailView,passwordView,mProgressView,connexionView,newAccountView);
        loginview.setListener();

    }
    @Override
    public void initializeMvc() {

        UserModelI userModel= new UserModel();
        UserControllerI userController=new UserController(userModel);
        loginview=((UserController)userController).getLoginView();

    }
    public static Context getlContext(){
        return lContext;
    }

}

