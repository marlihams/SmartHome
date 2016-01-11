package com.smarthome.Dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.smarthome.beans.Bean;
import com.smarthome.database.DatabaseHelper;
import com.smarthome.database.DatabaseManager;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public abstract class BaseDao<T extends Bean> implements SmartHomeDao<T> {

     protected   Context context;

    protected DatabaseHelper getHelper(){

        if (DatabaseManager.getInstance()==null)
                DatabaseManager.init(context);
        return DatabaseManager.getInstance().getHelper();
    }
    public Dao<T,Object> getConnection(){

            return getHelper().getBeanDao(getEntityClass());
    }

    private Class getEntityClass() {
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) t.getActualTypeArguments()[0];
    }
    @Override
    public List<T> findAll() {
        try {
            return (List<T>) getHelper().getBeanDao(getEntityClass()).queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    @Override
    public T findByPkey(Object id) {
        try {
            return (T) getHelper().getBeanDao(getEntityClass()).queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean createOrUpdate(T obj) {
        try {
            return getHelper().getBeanDao(getEntityClass()).createOrUpdate(obj).getNumLinesChanged() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }


    public boolean delete(T obj) {
        try {
            return getHelper().getBeanDao(getEntityClass()).delete(obj) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    public List<T> findAllByForeignKey(T obj,String foreignKey){

        try {
            return (List <T>) getHelper().getBeanDao(getEntityClass()).queryForEq(foreignKey,obj.getForeignKey(foreignKey));
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }

    }

    public List<T> findByAttribute(String attr,String value){

        try {
            return (List <T>) getHelper().getBeanDao(getEntityClass()).queryForEq(attr,value);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public void  emptyTable(){
        getHelper().emptyTable();
    }
    public void removeAll(){
        getHelper().removeAll();
    }


    public void createAll() {
        getHelper().createTable();
    }
}
