package com.smarthome.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smarthome.R;

import com.smarthome.android.HouseDetailActivity;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.SmartAnimation;
import com.smarthome.beans.User;
import com.smarthome.controller.HousesControllerI;
import com.smarthome.model.HomeAdapter;
import com.smarthome.beans.House;
import com.smarthome.model.HousesModel;
import com.smarthome.model.HousesModelI;
import com.smarthome.model.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mdiallo on 20/12/2015.
 */

public class HousesView implements SmartView,HouseObserver {

    public static String SELECTEDHOUSE="houseId";
    private RecyclerView listeHouses;
    private HousesControllerI housesController;
    private HousesModelI housesModel;
    private ImageButton addHouse;
    private ImageButton deleteHouse;
    private ImageButton refresh;




    public List<Integer> getPosition() {
        return position;
    }

    private List<Integer> position;
    int selected;


    public HousesView(HousesControllerI housesController,HousesModelI housesModel) {
        this.housesController = housesController;
        this.housesModel=housesModel;
        this.position = new ArrayList<Integer>();
         selected=-1;
        subscribeObserver();

    }

    public void addPosition(int position) {
        this.position.add(position);
    }

    public int getPosition(int key) {
        return this.position.get(key);
    }

    @Override
    public void initializeWidget(View... views) {
        listeHouses=(RecyclerView)views[0];
        addHouse=(ImageButton)views[1];
        deleteHouse=(ImageButton) views[2];
        refresh=(ImageButton) views[3];
        LinearLayoutManager lhouse = new LinearLayoutManager(HousesActivity.getlContext());
        listeHouses.setLayoutManager(lhouse);

    }

    @Override
    public void setListener() {
        listeHouses.setAdapter(housesController.getHousesModel().getAdapter());
        SmartAnimation.init(HousesActivity.getlContext());
        addHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(SmartAnimation.fad_in);
                addNewHouse();
            }
        });
        deleteHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(SmartAnimation.fad_in);
                if (position.isEmpty()) {
                    Toast.makeText(HousesActivity.getlContext(),"no item selected",Toast.LENGTH_SHORT).show();
                } else {
                    //TODO Gestion delete without selection
                  housesController.deleteHouse(position);
                    position.clear();
                }

            }
        });

        listeHouses.addOnItemTouchListener(new RecyclerItemClickListener(HousesActivity.getlContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                childView.startAnimation(SmartAnimation.fad_in);
                selected = position;
                changeView();
            }

            @Override
            public void onItemLongPress(View childView, int position) {

                if (getPosition().contains(position)) {
                    childView.setBackgroundResource(R.color.white);
                    getPosition().remove((Object) position);
                } else {
                    childView.setBackgroundResource(R.color.dark_gray);
                    getPosition().add(position);
                }
                childView.startAnimation(SmartAnimation.shake);
            }
        }));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(SmartAnimation.fad_in);
                //   initializeList();
                //TODO  barre de défilement  process long
                housesController.getHousesModel().initializeAdapter();
                listeHouses.setAdapter(housesController.getHousesModel().getAdapter());
            }
        });
    }

    @Override
    public void subscribeObserver() {
        housesModel.subscribeHouseObserver((HouseObserver)this);
    }

    public  void changeView(){

        if (selected >=0){
            House house=housesController.getHousesModel().getHouses().get(selected);
            Context ctx=HousesActivity.getlContext();
            Intent intent=new Intent(ctx, HouseDetailActivity.class);
            intent.putExtra(SELECTEDHOUSE,house.getId());
            ctx.startActivity(intent);
        }
    }

    @Override
    public void updateHouseObserver() {
        // update the view for the change or the delete of an item.
        housesController.getHousesModel().getAdapter().notifyDataSetChanged();
    }

     private void  addNewHouse(){

        final Dialog dialog = new Dialog(HousesActivity.getlContext());
        dialog.setContentView(R.layout.new_house);
        // set the custom dialog components - text, image and button
        final EditText houseName = (EditText) dialog.findViewById(R.id.homeName);
        final EditText houseAddress = (EditText) dialog.findViewById(R.id.home_address);
        Button cancel=(Button)dialog.findViewById(R.id.cancel);
        Button validate=(Button)dialog.findViewById(R.id.validate);
        // if button is clicked, close the custom dialog
        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String hName=houseName.getText().toString();
                String hAdress=houseAddress.getText().toString();
                if (hName.isEmpty() || hAdress.isEmpty()){
                    Toast.makeText(dialog.getContext(),"fill houseName and houseAdress field",Toast.LENGTH_SHORT).show();
                } else {
                    housesController.createNewHouse(hName,hAdress);
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
         dialog.show();
    }
}
