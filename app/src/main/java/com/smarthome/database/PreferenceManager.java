package com.smarthome.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class PreferenceManager implements CacheManagerI {

    private static String NAME = "preference";
    public static String DEFAULT="empty";
    public static PreferenceManager cacheManager;
    private static  SharedPreferences cache;

    public static PreferenceManager getInstance(Context ctx) {
        if (cache == null)
            cacheManager=new PreferenceManager(ctx);
            return cacheManager;
    }

    private PreferenceManager(Context context) {
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