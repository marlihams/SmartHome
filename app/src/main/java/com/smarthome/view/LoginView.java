package com.smarthome.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.smarthome.android.AcceuilActivity;
import com.smarthome.android.LoginActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.android.UserActivity;
import com.smarthome.beans.*;
import com.smarthome.controller.Test;
import com.smarthome.controller.UserController;
import com.smarthome.controller.UserControllerI;
import com.smarthome.model.UserModel;
import com.smarthome.model.UserModelI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class LoginView  implements SmartView,SpinnerObserver {
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private View progressView;
    private Button connexionView;
    private ImageButton  newAccountView;
    private Spinner spinner;
    private  List<House> houses;
    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    private int selectedElement;

    UserModelI userModel;
    UserControllerI userController;

    public LoginView(UserModelI userModel, UserControllerI userController) {
        this.userModel = userModel;
        this.userController = userController;
        subscribeObserver();
        houses=new ArrayList<House>();
    }
    @Override
    public void subscribeObserver(){

        this.userModel.subscribeSpinnerObserver((SpinnerObserver)this);
    }


    @Override
    public void initializeWidget(View... views) {
        emailView=(AutoCompleteTextView)views[0];
        passwordView=(EditText)views[1];
        progressView=views[2];
        connexionView=(Button)views[3];
        newAccountView=(ImageButton)views[4];
        spinner=(Spinner)views[5];

    }

    @Override
    public void setListener() {
        SmartAnimation.init(LoginActivity.getlContext());
        connexionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (houses.isEmpty()){
                    Toast.makeText(LoginActivity.getlContext(),"select a house first",Toast.LENGTH_SHORT).show();
                }else{
                userController.connect(houses.get(selectedElement), new User(emailView.getText().toString(), passwordView.getText().toString()));
                }
            }
        });

        newAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(LoginActivity.getlContext(), UserActivity.class);
                intent.putExtra(UserActivity.USER,true);
                LoginActivity.getlContext().startActivity(intent);

//                SmartChangeView.changeView(UserActivity.getlContext(), "user");
//                Log.i("CREATION", "NOT IMPLEMENTED");
            }

        });


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position,
                                           long id) {
                    selectedElement = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // NONE
                    String s = "salut";
                }
            });

            spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    String email = emailView.getText().toString();
                    String password = passwordView.getText().toString();
                    if ((!email.isEmpty() && !password.isEmpty()) && UserModel.isEmailValid(email) && houses.isEmpty()) {
                        User user = new User(email, password);
                        userController.updateHouse(user);

                    } else {
                        //ERROR
                        //TODO gestion des erreurs login
                        Log.e("ERROR", "EMAIL INVALIDE");
                    }
                    return false;
                }

            });
    }

    public void displayAcceuilView(User user){
        Intent intent=new Intent(LoginActivity.getlContext(),AcceuilActivity.class);
      ((Activity)   LoginActivity.getlContext()).startActivityForResult(intent, 100);
    }


    public UserModelI getUsermodel() {
        return userModel;
    }

    public void setUsermodel(UserModelI userModel) {
        this.userModel = userModel;
    }

    public UserControllerI getUserController() {
        return userController;
    }

    public void setUserController(UserControllerI userController) {
        this.userController = userController;
    }


    @Override
    public void updateSpinner(List<House> h) {
        setHouses(h);

        List<String> housesName=new ArrayList<>();
        // affiche les listes des maisons
        for (int i=0, len=h.size();i<len;i++){
            housesName.add(h.get(i).getName());
        }
        ArrayAdapter<String> adapter= new ArrayAdapter(LoginActivity.getlContext(),android.R.layout.simple_spinner_item,housesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}