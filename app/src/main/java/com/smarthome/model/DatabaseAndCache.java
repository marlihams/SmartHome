package com.smarthome.model;

import android.content.Context;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.BeanCache.UserCacheDao;
import com.smarthome.Dao.DeviceDao;
import com.smarthome.Dao.HistoriqueDao;
import com.smarthome.Dao.HouseDao;
import com.smarthome.Dao.UserDao;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.beans.House;
import com.smarthome.beans.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 06/01/2016.
 */
public class DatabaseAndCache {

    private static UserDao userDao;
    private static HouseDao houseDao;
    private static DeviceDao deviceDao;
    private static HistoriqueDao historiqueDao;


    // cache
    private static UserCacheDao userCacheDao;
    private  static HouseCacheDao houseCacheDao;
    private  static DeviceCacheDao deviceCacheDao;
    private  static HistoriqueCacheDao historiqueCacheDao;
    private static Context ctx;

    public static void  init (Context ct) {
        if (ctx == null) {
            ctx = ct;
            userDao = UserDao.getInstance(ctx);
            houseDao = HouseDao.getInstance(ctx);
             deviceDao=DeviceDao.getInstance(ctx);
             historiqueDao=HistoriqueDao.getInstance(ctx);

            // cache Dao
            houseCacheDao = new HouseCacheDao(ctx);
            userCacheDao = new UserCacheDao(ctx);
            deviceCacheDao=new DeviceCacheDao(ctx);
            historiqueCacheDao=new HistoriqueCacheDao(ctx);

        }
    }
    public static void loadDataInCache (User user) {
        //TODO in different thread;

        userCacheDao.createOrUpdate(user);
        // houses
       List<House>houses=loadHouse(user);
        houseCacheDao.addElements(houses);
        //devices
        List<Device>devices=loadDevice(houses);
        deviceCacheDao.addElements(devices);
        List<Historique> historiques=loadHistorique((ArrayList<House>) houses);
        historiques.addAll(loadHistorique(devices));
        historiqueCacheDao.addElements(historiques);
        String a="slt";

    }

    public static List<Historique>loadHistorique(List<Device> devices){
        List<Historique>historiques=new ArrayList<Historique>();
        Historique historique=new Historique();
        for (int i=0,len=devices.size();i<len;i++){
            historique.setDevice(devices.get(i));
           historiques.addAll(historiqueDao.findAllByForeignKey(historique,"device_id"));
        }
        return historiques;

    }

    private static List<House>  loadHouse(User user){

        House house = new House();
        house.setUser(user);
        List<House> houses = houseDao.findAllByForeignKey(house, "user_id");
        return houses;
    }
    private static List<Device>  loadDevice(List<House>houses){
        List<Device>devices=new ArrayList<Device>();
        Device device=new Device();

        for (int i=0,len=houses.size();i<len;i++){
            device.setHouse(houses.get(i));
            devices.addAll(deviceDao.findAllByForeignKey(device, "house_id"));
        }
        return devices;
    }
    public static List<Historique>loadHistorique(ArrayList<House> houses){
        List<Historique>historiques=new ArrayList<Historique>();
        Historique historique=new Historique();
        for (int i=0,len=houses.size();i<len;i++){
            historique.setHouse(houses.get(i));
            historiques.addAll(historiqueDao.findAllByForeignKey(historique, "house_id"));
        }
        return historiques;

    }

}
