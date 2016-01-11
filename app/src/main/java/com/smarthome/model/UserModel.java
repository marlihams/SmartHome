package com.smarthome.model;

import android.util.Log;
import android.widget.Toast;

import com.smarthome.BeanCache.HouseCacheDao;
import com.smarthome.BeanCache.UserCacheDao;
import com.smarthome.Dao.HouseDao;
import com.smarthome.Dao.UserDao;
import com.smarthome.android.HousesActivity;
import com.smarthome.android.LoginActivity;
import com.smarthome.android.UserActivity;
import com.smarthome.beans.House;
import com.smarthome.beans.User;
import com.smarthome.controller.Test;
import com.smarthome.view.SpinnerObserver;
import com.smarthome.view.UserObsever;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class UserModel implements UserModelI {

    private ArrayList<UserObsever> userobservers=new ArrayList<UserObsever>();
    private ArrayList<SpinnerObserver>spinnerObservers=new ArrayList<>();
    private UserCacheDao userCacheDao;
    private HouseCacheDao houseCacheDao;
    private UserDao userDao;
    private HouseDao houseDao;
    private User user;

    public UserModel(boolean connection) {
     //   databaseManager=new DatabaseManager(UserActivity.getlContext());
        userDao=UserDao.getInstance(connection ? LoginActivity.getlContext():UserActivity.getlContext());
        houseDao=HouseDao.getInstance(connection?LoginActivity.getlContext(): HousesActivity.getlContext());
        houseCacheDao=new HouseCacheDao(connection ? LoginActivity.getlContext():UserActivity.getlContext());
        userCacheDao=new UserCacheDao(connection ? LoginActivity.getlContext():UserActivity.getlContext());
        user=null;
}

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public User getUserByEmailAndPassword(User user) {
        try {
           List<User> users=(List<User>) userDao.getConnection().queryBuilder().where()
                   .eq("user_email", user.getEmail()).and()
                   .eq("user_password", user.getPassword()).query();
            Log.i("taille",""+users.size());
            return users.isEmpty() ? null:users.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<House> getAllHouses() {
        return user !=null ? houseCacheDao.findAll() :Collections.EMPTY_LIST;
    }



    @Override
    public void subscribeUserObserver(UserObsever observer) {
        userobservers.add(observer);
    }


    @Override
    public void subscribeSpinnerObserver(SpinnerObserver observer) {
        spinnerObservers.add(observer);
    }

    @Override
    public void removeObserver(UserObsever observer) {
        int index=userobservers.indexOf(observer);
        if (index >=0)
            userobservers.remove(index);
    }

    @Override
    public void updateHouse(User user)  {
    //  userDao.emptyTable();

       /*     try {

             //   userDao.getConnection().executeRaw("drop database smarthome.db");

            } catch (SQLException e) {
                e.printStackTrace();
            }


        userDao.removeAll();
        userDao.createAll();
            Test.init(LoginActivity.getlContext());
            Test.fillDatabase();*/
         //   userCacheDao.deleteAll();

        User userCache = userCacheDao.getUserFromCache(user); // check is user is already on cache
        if (userCache == null || !(user.getEmail().equals(userCache.getEmail())) ||!(user.getPassword().equals(userCache.getPassword())) ) { //check on database
            userCache = getUserByEmailAndPassword(user);
            if (userCache != null) {
                DatabaseAndCache.init(LoginActivity.getlContext());
                DatabaseAndCache.loadDataInCache(userCache);


            }
            else{

                Toast.makeText(LoginActivity.getlContext(),"wrong email and password",Toast.LENGTH_SHORT).show();
            }

        }

            this.user=userCache;
        notifySpinnerObserver();

    }



    @Override
    public void notifyUserObserver() {
        int len=userobservers.size();
        for (int i=0;i<len;i++)
            userobservers.get(i).updateUserInfo();
    }

    @Override
    public void notifySpinnerObserver() {
        int len=spinnerObservers.size();
        List<House> houses=getAllHouses();

        for (int i=0;i<len;i++)
            spinnerObservers.get(i).updateSpinner(houses);
    }
      public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
