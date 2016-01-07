package com.smarthome.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.smarthome.beans.User;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class CacheManager implements CacheManagerI {

    private static String NAME = "smarthome";
    public static String DEFAULT="empty";
    public static CacheManager cacheManager;
    private static  SharedPreferences cache;

    public static CacheManager getInstance(Context ctx) {
        if (cache == null)
            cacheManager=new CacheManager(ctx);
            return cacheManager;
    }

    private CacheManager(Context context) {
        cache = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public String findElement(String key) {

        return cache.getString(key, DEFAULT);
    }

    public boolean addElement(String key, String value) {
        SharedPreferences.Editor editor=cache.edit();
       return  editor.putString(key, value).commit();
//       boolean bool= editor.commit();
//        String s=cache.getString(key, DEFAULT);
//
//        return bool;
    }

    public  boolean removeElement(String key) {
        cache.edit().remove(key);
        return cache.edit().commit();

    }

    public void deleteAll() {
        SharedPreferences.Editor editor=cache.edit();
        editor.clear().commit();
    }

}