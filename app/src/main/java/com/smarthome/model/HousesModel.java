package com.smarthome.model;

import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.android.HousesActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.House;
import com.smarthome.electronic.ElectronicManager;
import com.smarthome.view.HouseObserver;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public class HousesModel  implements  HousesModelI{

    private  List<House> houses;
    private List<HouseObserver> houseObservers=new ArrayList<HouseObserver>();

    private HouseCacheDao houseCacheDao;
    private ElectronicManager electronicManager;
    private List<Device> devices;
    private HomeAdapter adapter;
    private   List<String> conso;

    @Override
    public HomeAdapter getAdapter() {
        return adapter;
    }
    @Override
    public void setAdapter(HomeAdapter adapter) {
        this.adapter = adapter;
    }

    public HousesModel() {

        houseCacheDao=new HouseCacheDao(HousesActivity.getlContext());
         houses= houseCacheDao.findAll();
        initializeAdapter();

    }

    @Override
    public List<House> getHouses(){
        return  houses;
    }

    @Override
    public void subscribeHouseObserver(HouseObserver observer) {
        this.houseObservers.add(observer);
    }

    @Override
    public void notifyHouseObserver() {

        for (int i=0;i<houseObservers.size();i++){
            houseObservers.get(i).updateHouseObserver();
        }
    }

    @Override
    public void updateCache() {
        houseCacheDao.addElements(adapter.getHouses());
        houses=adapter.getHouses();
        notifyHouseObserver();
    }
    @Override
    public  void updateAdapter(House house,String conso){

        adapter.addItem(house,addSymbole(conso));
        updateCache();
    }
    private List<String> getConsoHouse(){

        //TODO  or each house get all device and then add their consomation
        int min=50;
        int max=100;
        List<String> conso=new ArrayList<String>();
        int a;
        for (int i=0,len=houses.size();i<len; i++) {
            a = (int) (min + Math.random() * (max - min + 1));
            conso.add(addSymbole(""+a));
        }
        return conso;
    }
    public  void initializeAdapter() {
        adapter=null;
        adapter=new HomeAdapter(getHouses(),getConsoHouse());
    }
    private  String addSymbole(String a){
        return a+ " "+ Currency.getInstance(Locale.getDefault()).getSymbol();
    }

}
