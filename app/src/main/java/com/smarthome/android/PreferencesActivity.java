package com.smarthome.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.smarthome.R;
import com.smarthome.controller.PreferencesController;
import com.smarthome.controller.PreferencesControllerI;
import com.smarthome.model.PreferencesModel;
import com.smarthome.model.PreferencesModelI;
import com.smarthome.view.PreferencesView;
import com.smarthome.view.SmartHomeView;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class PreferencesActivity extends Activity implements SmartHomeView {
    protected static Context lContext;
    private PreferencesView preferencesView;


    private RadioGroup connectionMode;
    private ImageButton back;
    private ImageButton submit;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preference);
        lContext=this;

        connectionMode=(RadioGroup)findViewById(R.id.radioconnection_mode);
        back=(ImageButton)findViewById(R.id.back);
        submit=(ImageButton)findViewById(R.id.submit);
        price=(EditText)findViewById(R.id.price_edit);

        initializeMvc();

        preferencesView.initializeWidget(connectionMode, back,submit,price);
        preferencesView.setListener();


    }

    public static Context getlContext() {
        return lContext;
    }

    @Override
    public void initializeMvc() {
        PreferencesModelI preferencesModel=new PreferencesModel(lContext);
        PreferencesControllerI preferencesController=new PreferencesController(preferencesModel);
        preferencesView= (PreferencesView)((PreferencesController)preferencesController).getView();
    }


}
