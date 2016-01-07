package com.smarthome.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.smarthome.R;
import com.smarthome.view.SmartHomeView;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserActivity extends Activity implements SmartHomeView {
    protected static Context lContext;
    public static String USER = "newUser";
    private boolean newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO change layout
        setContentView(R.layout.activity_user);
        lContext=this;


    }

    public static Context getlContext() {
        return lContext;
    }

//    @Override
//    protected void initialize() {
//        super.initialize();
//    }

    @Override
    public void initializeMvc() {

    }

}
