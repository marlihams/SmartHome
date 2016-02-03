package com.smarthome.view;

import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.smarthome.R;
import com.smarthome.android.DevicesActivity;
import com.smarthome.android.PreferencesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.controller.PreferencesControllerI;
import com.smarthome.database.PreferenceManager;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class PreferencesView implements  SmartView{

    PreferencesControllerI preferencesController;

    private RadioGroup connectionMode;
    private ImageButton back;
    private ImageButton submit;
    private EditText price;
    public static String MODECONNECTION="connectionMode";
    public static String PRICE="price";

    public PreferencesView( PreferencesControllerI preferencesController) {

        this.preferencesController = preferencesController;

    }

    @Override
    public void initializeWidget(View... views) {

//        bluetooth=(RadioButton)views[0];
//        router=(RadioButton)views[1];
        connectionMode=(RadioGroup)views[0];
        back=(ImageButton)views[1];;
        submit=(ImageButton)views[2];
        price=(EditText)views[3];
        setWidgetValue();


    }

    private void setWidgetValue() {
        String modeDefault=preferencesController.getPreferencesModel().findElement(MODECONNECTION);
        if(modeDefault.equals("router")) {
            connectionMode.check(R.id.router);
            BluetoothAdapter.getDefaultAdapter().disable();
            SmartChangeView.setBluetooth(false);
        }
        else {
            connectionMode.check(R.id.bluetooth);
            BluetoothAdapter.getDefaultAdapter().enable();
            SmartChangeView.setBluetooth(true);
        }

        String prix=preferencesController.getPreferencesModel().findElement(PRICE);
        if (!prix.equals(PreferenceManager.DEFAULT))
            price.setText(prix);
    }

    @Override
    public void setListener() {
        SmartAnimation.init(DevicesActivity.getlContext());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.fad_in);
                SmartChangeView.changeView(PreferencesActivity.getlContext(), "acceuil");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.fad_in);
             if (connectionMode.getCheckedRadioButtonId()==R.id.router){
                 preferencesController.getPreferencesModel().addElement(MODECONNECTION,"router");
                 SmartChangeView.setBluetooth(false);
             }
              else {
                 preferencesController.getPreferencesModel().addElement(MODECONNECTION, "bluetooth");
                 SmartChangeView.setBluetooth(true);

             }

                String prix=price.getText().toString();
                if (prix.isEmpty())
                    Toast.makeText(PreferencesActivity.getlContext(),"the price can not be null",Toast.LENGTH_SHORT).show();

                else {
                    preferencesController.getPreferencesModel().addElement(PRICE, prix);
                    Toast.makeText(PreferencesActivity.getlContext(), "Preferences has been saved", Toast.LENGTH_SHORT).show();
                }
            }

        });
        connectionMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.router)
                    BluetoothAdapter.getDefaultAdapter().disable();
                else
                    BluetoothAdapter.getDefaultAdapter().enable();
            }
        });

    }
    @Override
    public void subscribeObserver() {

    }
}
