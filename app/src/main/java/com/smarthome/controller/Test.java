package com.smarthome.controller;

import android.content.Context;

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
 * Created by Mdiallo on 23/12/2015.
 */
public class Test {

   private static  UserDao userDao;
    private static  HouseDao houseDao;
    private static   DeviceDao deviceDao;
    private static HistoriqueDao historiqueDao;

    public static  void init(Context ctx){
        userDao=UserDao.getInstance(ctx);
        houseDao=HouseDao.getInstance(ctx);
        deviceDao=DeviceDao.getInstance(ctx);
        houseDao=houseDao.getInstance(ctx);
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
        House house1=new House("Winston st house","4 rue winston churchill 60200 compiegne",user1);
        House house2=new House("Emille st house","1 rue docteur roux 60200 compiegne",user1);
        House house3=new House("Pierre st house","4 rue de  pierre  60200 compiegne",user1);
        
//Device(House house,String name,String pieceName, adress) {
        Device device1=new Device(house1,"plafond cuisine","cuisine");
        Device device2=new Device(house1,"plafond salon","salon");
        Device device3=new Device(house1,"hote cuisine","cuisine");
        Device device4=new Device(house1,"prise tv","salon");
        Device device5=new Device(house1,"lampe chevet", "Chambre 1");
        Device device6=new Device(house1,"lampe table", "Chambre 1");
        Device device7=new Device(house1,"prise ordi","salon");
        Device device8=new Device(house1,"lampe table", "Chambre 2");
        Device device9=new Device(house1,"lampe chevet", "Chambre 2");


        Device device10=new Device(house2,"plafond cuisine","cuisine");
        Device device11=new Device(house2,"plafond salon","salon");
        Device device12=new Device(house2,"hote cuisine","cuisine");
        Device device13=new Device(house2,"prise tv","salon");
        Device device14=new Device(house2,"lampe chevet", "Chambre 1");
        Device device15=new Device(house2,"lampe table", "Chambre 1");
        Device device16=new Device(house2,"prise ordi","salon");
        Device device17=new Device(house2,"lampe table", "Chambre 2");
        Device device18=new Device(house2,"lampe chevet", "Chambre 2");

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
        deviceDao.createOrUpdate(device13);
        deviceDao.createOrUpdate(device14);
        deviceDao.createOrUpdate(device15);
        deviceDao.createOrUpdate(device16);
        deviceDao.createOrUpdate(device17);
        deviceDao.createOrUpdate(device18);


// Historique(String dateDebut, String dateFin, ConsoDevice deviceConso, house houseConso) {

            // conso Device 1
        historiqueDao.createOrUpdate(new Historique("01-2015",device1,54));
        historiqueDao.createOrUpdate(new Historique("02-2015",device1,64));
        historiqueDao.createOrUpdate(new Historique("03-2015",device1,23));
        historiqueDao.createOrUpdate(new Historique("04-2015",device1,89));
        historiqueDao.createOrUpdate(new Historique("05-2015",device1,23));
        historiqueDao.createOrUpdate(new Historique("06-2015",device1,79));


        historiqueDao.createOrUpdate(new Historique("01-2015",device2,44));
        historiqueDao.createOrUpdate(new Historique("02-2015",device2,54));
        historiqueDao.createOrUpdate(new Historique("03-2015",device2,53));
        historiqueDao.createOrUpdate(new Historique("04-2015",device2,79));
        historiqueDao.createOrUpdate(new Historique("05-2015",device2,33));
        historiqueDao.createOrUpdate(new Historique("06-2015",device2,69));

        historiqueDao.createOrUpdate(new Historique("01-2015",house1,144));
        historiqueDao.createOrUpdate(new Historique("02-2015",house1,154));
        historiqueDao.createOrUpdate(new Historique("03-2015",house1,153));
        historiqueDao.createOrUpdate(new Historique("04-2015",house1,179));
        historiqueDao.createOrUpdate(new Historique("05-2015",house1,133));
        historiqueDao.createOrUpdate(new Historique("06-2015",house1,169));

        historiqueDao.createOrUpdate(new Historique("01-2015",house2,114));
        historiqueDao.createOrUpdate(new Historique("02-2015",house2,104));
        historiqueDao.createOrUpdate(new Historique("03-2015",house2,153));
        historiqueDao.createOrUpdate(new Historique("04-2015",house2,139));
        historiqueDao.createOrUpdate(new Historique("05-2015",house2,135));
        historiqueDao.createOrUpdate(new Historique("06-2015",house2,169));

    }

    private static void dropAllTable() {

//        TableUtils.dropTable(connectionSource, User.class, true);
//        TableUtils.dropTable(connectionSource, House.class, true);
//        TableUtils.dropTable(connectionSource, Device.class, true);
//        TableUtils.dropTable(connectionSource, ConsoDevice.class, true);
//        TableUtils.dropTable(connectionSource, house.class, true);
//        TableUtils.dropTable(connectionSource, Historique.class, true);

    }

}
