package com.smarthome.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smarthome.R;
import com.smarthome.beans.House;

import java.util.List;

/**
 * Created by Mdiallo on 01/01/2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>  {


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView houseName;
        TextView houseAdress;
        Button houseConso;
        int position=0;

        HomeViewHolder(View itemView) {
            super(itemView);
           cv = (CardView) itemView.findViewById(R.id.house_card);
            houseName = (TextView) itemView.findViewById(R.id.house_name);
            houseAdress = (TextView) itemView.findViewById(R.id.house_address);
            houseConso=(Button)itemView.findViewById(R.id.houseConso);
            houseConso.setTag(position);
            itemView.setClickable(true);

        }

    }
    List<House> houses;
    List<String> consomation;
    public int position=0;

    public HomeAdapter(List<House> houses, List<String> consomation) {
        this.houses = houses;
        this.consomation = consomation;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_houses, viewGroup, false);
        position++;
        HomeViewHolder homeViewHolder = new HomeViewHolder(v);


        return homeViewHolder;
    }
    @Override
    public void onBindViewHolder(final HomeViewHolder homeViewHolder, int i) {
        homeViewHolder.houseName.setText(houses.get(i).getName());
        homeViewHolder.houseAdress.setText(houses.get(i).getAddress());
        homeViewHolder.houseConso.setText(consomation.get(i));
        homeViewHolder.houseConso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=5;
            }
        });


    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void addItem(House house,String conso){
        houses.add(house);
        consomation.add(conso);

    }
    public  void removeItem(int index){
        houses.remove(index);
        consomation.remove((Object) index);
    }

    public List<House> getHouses() {
        return houses;
    }
}
