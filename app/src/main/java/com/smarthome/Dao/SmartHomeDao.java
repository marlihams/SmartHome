package com.smarthome.Dao;

import java.util.List;

/**
 * Created by Mdiallo on 26/12/2015.
 */
public interface SmartHomeDao <T> {
    public T findByPkey(Object id);
    public boolean createOrUpdate(T obj);
    public boolean delete(T obj);
    public List<T> findAll();

}
