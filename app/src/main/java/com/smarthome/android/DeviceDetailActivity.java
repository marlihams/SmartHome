package com.smarthome.android;

import android.content.Context;
import android.os.Bundle;

import com.smarthome.R;
import com.smarthome.view.SmartHomeView;

/**
 * Created by Mdiallo on 23/12/2015.
 */
public class DeviceDetailActivity extends  SmartMenuActivity implements SmartHomeView {

    private static Context lContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        super.initialize();
        lContext=this;
        // creation controller view model
        initializeMvc();

        //getting object from their Ids

    }

    @Override
    public void initializeMvc() {


    }

    public static Context getlContext() {
        return lContext;
    }
}