package com.smarthome.model;

import android.content.Context;

import com.smarthome.BeanCache.ConsoDeviceCacheDao;
import com.smarthome.BeanCache.ConsoHouseCacheDao;
import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.BeanCache.UserCacheDao;
import com.smarthome.Dao.BaseDao;
import com.smarthome.Dao.ConsoDeviceDao;
import com.smarthome.Dao.ConsoHouseDao;
import com.smarthome.Dao.DeviceDao;
import com.smarthome.Dao.HistoriqueDao;
import com.smarthome.Dao.HouseDao;
import com.smarthome.Dao.UserDao;
import com.smarthome.beans.Bean;
import com.smarthome.beans.ConsoDevice;
import com.smarthome.beans.ConsoHouse;
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
    private static ConsoHouseDao consoHouseDao;
    private static ConsoDeviceDao consoDeviceDao;


    // cache
    private static UserCacheDao userCacheDao;
    private  static HouseCacheDao houseCacheDao;
    private  static DeviceCacheDao deviceCacheDao;
    private  static HistoriqueCacheDao historiqueCacheDao;
    private  static ConsoHouseCacheDao consoHouseCacheDao;
    private  static ConsoDeviceCacheDao consoDeviceCacheDao;
    private static Context ctx;

    public static void  init (Context ct) {
        if (ctx == null) {
            ctx = ct;
            userDao = UserDao.getInstance(ctx);
            houseDao = HouseDao.getInstance(ctx);
             deviceDao=DeviceDao.getInstance(ctx);
             historiqueDao=HistoriqueDao.getInstance(ctx);
            consoHouseDao=ConsoHouseDao.getInstance(ctx);
             consoDeviceDao=ConsoDeviceDao.getInstance(ctx);

            // cache Dao
            houseCacheDao = new HouseCacheDao(ctx);
            userCacheDao = new UserCacheDao(ctx);
            deviceCacheDao=new DeviceCacheDao(ctx);
            historiqueCacheDao=new HistoriqueCacheDao(ctx);
            consoDeviceCacheDao=new ConsoDeviceCacheDao(ctx);
            consoHouseCacheDao=new ConsoHouseCacheDao(ctx);

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
        // consoDevice
        List<ConsoDevice>consoDevices=loadConsoDevice(devices);
        //consoHouse
        List<ConsoHouse>consoHouses=loadConsoHouse(houses);
        List<Historique> historiques=loadHistorique(consoHouses);
        historiqueCacheDao.addElements(historiques);




    }
    private static List<Historique>loadHistorique(List<ConsoHouse> consoHouses){
        List<Historique>historiques=new ArrayList<Historique>();
        Historique historique=new Historique();

        for (int i=0,len=consoHouses.size();i<len;i++){
            historique.setHouseConso(consoHouses.get(i));
            historiques.addAll(historiqueDao.findAllByForeignKey(historique, "houseConso_id"));
        }
        return historiques;

    }
    private static List<ConsoHouse>  loadConsoHouse(List<House> houses){
        // consoHouse
        List<ConsoHouse>consoHouses=new ArrayList<ConsoHouse>();
        ConsoHouse consoHouse=new ConsoHouse();

        for (int i=0,len=houses.size();i<len;i++){
            consoHouse.setHouse(houses.get(i));
            consoHouses.addAll(consoHouseDao.findAllByForeignKey(consoHouse, "house_id"));
        }
        return consoHouses;
    }
    private static List<ConsoDevice> loadConsoDevice(List<Device> devices){
        // consoDevice

        List<ConsoDevice>consoDevices=new ArrayList<ConsoDevice>();
        ConsoDevice consoDevice=new ConsoDevice();

        for (int i=0,len=devices.size();i<len;i++){
            consoDevice.setDevice(devices.get(i));
            consoDevices.addAll(consoDeviceDao.findAllByForeignKey(consoDevice, "device_id"));
        }
        return consoDevices;

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

}
