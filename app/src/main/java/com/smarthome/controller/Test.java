package com.smarthome.controller;

import android.content.Context;

import com.j256.ormlite.table.TableUtils;
import com.smarthome.Dao.ConsoDeviceDao;
import com.smarthome.Dao.ConsoHouseDao;
import com.smarthome.Dao.DeviceDao;
import com.smarthome.Dao.HistoriqueDao;
import com.smarthome.Dao.HouseDao;
import com.smarthome.Dao.UserDao;
import com.smarthome.android.LoginActivity;
import com.smarthome.beans.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 23/12/2015.
 */
public class Test {

   private static  UserDao userDao;
    private static  HouseDao houseDao;
    private static   DeviceDao deviceDao;
    private static ConsoHouseDao consoHouseDao;
    private  static ConsoDeviceDao consoDeviceDao;
    private static HistoriqueDao historiqueDao;

    public static  void init(Context ctx){
        userDao=UserDao.getInstance(ctx);
        houseDao=HouseDao.getInstance(ctx);
        deviceDao=DeviceDao.getInstance(ctx);
        consoDeviceDao=ConsoDeviceDao.getInstance(ctx);
        consoHouseDao=ConsoHouseDao.getInstance(ctx);
        historiqueDao=HistoriqueDao.getInstance(ctx);
    }


    public static  void fillDatabase(){

        List<User> userList= new ArrayList<>();

        User user1=new User("diallo" , "madiou","smarthome","mohamedmadioud@yahoo.fr","0605775396");
        User user2=new User ("diallo" , "madiou","smarthome","b@yahoo.fr","0609775396");
        User user3=new User ("c@yahoo.fr","smarthome");
        User user4=new User ("d@yahoo.fr","smarthome");
        User user5=new User ("e@yahoo.fr","smarthome");
        // userList.add(user2,user3,user4,user5);

   //     public House(String name, String address, User user)
        House house1=new House("home 1","4 rue winston churchill 60200 compiegne",user1);
        House house2=new House("home 2","1 rue docteur roux 60200 compiegne",user1);
        House house3=new House("home 3","4 rue de  pierre  60200 compiegne",user1);
        House house4=new House(user2,"home4");
        House house5=new House(user2,"home5");
//Device(House house,String name,String pieceName) {
        Device device1=new Device(house1,"ampoule 1","piece 1");
        Device device2=new Device(house1,"ampoule 2","piece 2");
        Device device3=new Device(house1,"ampoule 3","piece 1");
        Device device6=new Device(house1,"ampoule 4","piece 2");
        Device device4=new Device(house1,"ampoule4", "piece 2");
        Device device5=new Device(house1,"ampoule4", "piece 2");

        Device device7=new Device(house2,"ampoule 2","piece 2");
        Device device8=new Device(house2,"ampoule 3","piece 1");
        Device device9=new Device(house2,"ampoule 4","piece 2");
        Device device10=new Device(house2,"ampoule4", "piece 2");
        Device device11=new Device(house2,"ampoule4", "piece 2");
        Device device12=new Device(house2,"ampoule 1","piece 1");

        //  add bean user
        userDao.createOrUpdate(user1);
        userDao.createOrUpdate(user2);
        userDao.createOrUpdate(user3);
        userDao.createOrUpdate(user4);
        userDao.createOrUpdate(user5);

        // add house
        houseDao.createOrUpdate(house1);
        houseDao.createOrUpdate(house2);
        houseDao.createOrUpdate(house3);
        houseDao.createOrUpdate(house4);
        houseDao.createOrUpdate(house5);

        //add device
        deviceDao.createOrUpdate(device1);
        deviceDao.createOrUpdate(device2);
        deviceDao.createOrUpdate(device3);
        deviceDao.createOrUpdate(device4);
        deviceDao.createOrUpdate(device5);
        deviceDao.createOrUpdate(device6);
        deviceDao.createOrUpdate(device7);
        deviceDao.createOrUpdate(device8);
        deviceDao.createOrUpdate(device9);
        deviceDao.createOrUpdate(device10);
        deviceDao.createOrUpdate(device11);
        deviceDao.createOrUpdate(device12);


        //ConsoDevice(String consomation, Device device) {
        ConsoDevice consoDevice1=new ConsoDevice("54",device1);
        ConsoDevice consoDevice2=new ConsoDevice("64",device1);
        ConsoDevice consoDevice3=new ConsoDevice("84", device1);
        ConsoDevice consoDevice4=new ConsoDevice("90",device1);
        ConsoDevice consoDevice5=new ConsoDevice("200",device2);
        ConsoDevice consoDevice6=new ConsoDevice("54",device2);
        ConsoDevice consoDevice7=new ConsoDevice("64",device2);
        ConsoDevice consoDevice8=new ConsoDevice("84", device2);
        ConsoDevice consoDevice9=new ConsoDevice("90",device3);
        ConsoDevice consoDevice10=new ConsoDevice("200",device3);
        ConsoDevice consoDevice11=new ConsoDevice("64",device3);
        ConsoDevice consoDevice12=new ConsoDevice("84", device3);
        ConsoDevice consoDevice13=new ConsoDevice("90",device4);
        ConsoDevice consoDevice14=new ConsoDevice("200",device4);
        ConsoDevice consoDevice15=new ConsoDevice("200",device5);

        consoDeviceDao.createOrUpdate(consoDevice1);
        consoDeviceDao.createOrUpdate(consoDevice2);
        consoDeviceDao.createOrUpdate(consoDevice3);
        consoDeviceDao.createOrUpdate(consoDevice4);
        consoDeviceDao.createOrUpdate(consoDevice5);
        consoDeviceDao.createOrUpdate(consoDevice6);
        consoDeviceDao.createOrUpdate(consoDevice7);
        consoDeviceDao.createOrUpdate(consoDevice8);
        consoDeviceDao.createOrUpdate(consoDevice9);
        consoDeviceDao.createOrUpdate(consoDevice10);
        consoDeviceDao.createOrUpdate(consoDevice11);
        consoDeviceDao.createOrUpdate(consoDevice12);
        consoDeviceDao.createOrUpdate(consoDevice13);
        consoDeviceDao.createOrUpdate(consoDevice1);
        consoDeviceDao.createOrUpdate(consoDevice15);


        ConsoHouse consoHouse1=new ConsoHouse("54",house1);
        ConsoHouse consoHouse2=new ConsoHouse("64",house1);
        ConsoHouse consoHouse3=new ConsoHouse("84", house1);
        ConsoHouse consoHouse4=new ConsoHouse("90",house1);
        ConsoHouse consoHouse5=new ConsoHouse("200",house1);

        ConsoHouse consoHouse6=new ConsoHouse("54",house2);
        ConsoHouse consoHouse7=new ConsoHouse("64",house2);
        ConsoHouse consoHouse8=new ConsoHouse("84", house2);
        ConsoHouse consoHouse9=new ConsoHouse("90",house2);
        ConsoHouse consoHouse10=new ConsoHouse("200",house2);

        ConsoHouse consoHouse11=new ConsoHouse("80",house3);
        ConsoHouse consoHouse12=new ConsoHouse("85", house3);
        ConsoHouse consoHouse13=new ConsoHouse("100",house3);
        ConsoHouse consoHouse14=new ConsoHouse("250",house3);

        consoHouseDao.createOrUpdate(consoHouse1);
        consoHouseDao.createOrUpdate(consoHouse2);
        consoHouseDao.createOrUpdate(consoHouse3);
        consoHouseDao.createOrUpdate(consoHouse4);
        consoHouseDao.createOrUpdate(consoHouse5);
        consoHouseDao.createOrUpdate(consoHouse6);
        consoHouseDao.createOrUpdate(consoHouse7);
        consoHouseDao.createOrUpdate(consoHouse8);
        consoHouseDao.createOrUpdate(consoHouse9);
        consoHouseDao.createOrUpdate(consoHouse10);
        consoHouseDao.createOrUpdate(consoHouse11);
        consoHouseDao.createOrUpdate(consoHouse12);
        consoHouseDao.createOrUpdate(consoHouse13);
        consoHouseDao.createOrUpdate(consoHouse14);


// Historique(String dateDebut, String dateFin, ConsoDevice deviceConso, ConsoHouse houseConso) {

            // conso Device 1
        Historique historique1=new Historique("01-01-2015","31-01-2015",consoDevice1,consoHouse1);
        Historique historique2=new Historique("01-02-2015","29-02-2015",consoDevice2,consoHouse1);
        Historique historique3=new Historique("01-03-2015","30-03-2015",consoDevice3,consoHouse1);
        Historique historique4=new Historique("01-04-2015","30-04-2015",consoDevice4,consoHouse1);


        Historique historique5=new Historique("01-01-2015","31-01-2015",consoDevice5,consoHouse2);
        Historique historique6=new Historique("01-02-2015","29-02-2015",consoDevice6,consoHouse2);
        Historique historique7=new Historique("01-03-2015","30-03-2015",consoDevice7,consoHouse2);
        Historique historique8=new Historique("01-04-2015","30-04-2015",consoDevice8,consoHouse2);

        Historique historique9=new Historique("01-01-2015","31-01-2015",consoDevice9,consoHouse3);
        Historique historique10=new Historique("01-02-2015","29-02-2015",consoDevice10,consoHouse3);
        Historique historique11=new Historique("01-03-2015","30-03-2015",consoDevice11,consoHouse3);
        Historique historique12=new Historique("01-04-2015","30-04-2015",consoDevice12,consoHouse3);


        historiqueDao.createOrUpdate(historique1);
        historiqueDao.createOrUpdate(historique2);
        historiqueDao.createOrUpdate(historique3);
        historiqueDao.createOrUpdate(historique4);
        historiqueDao.createOrUpdate(historique5);
        historiqueDao.createOrUpdate(historique6);
        historiqueDao.createOrUpdate(historique7);
        historiqueDao.createOrUpdate(historique8);
        historiqueDao.createOrUpdate(historique9);
        historiqueDao.createOrUpdate(historique10);
        historiqueDao.createOrUpdate(historique11);
        historiqueDao.createOrUpdate(historique12);


        String  a="slt";



    }

    private static void dropAllTable() {

//        TableUtils.dropTable(connectionSource, User.class, true);
//        TableUtils.dropTable(connectionSource, House.class, true);
//        TableUtils.dropTable(connectionSource, Device.class, true);
//        TableUtils.dropTable(connectionSource, ConsoDevice.class, true);
//        TableUtils.dropTable(connectionSource, ConsoHouse.class, true);
//        TableUtils.dropTable(connectionSource, Historique.class, true);

    }

}
