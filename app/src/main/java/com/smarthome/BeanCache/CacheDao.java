package com.smarthome.BeanCache;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smarthome.Dao.SmartHomeDao;
import com.smarthome.beans.Bean;
import com.smarthome.database.CacheManager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mdiallo on 25/12/2015.
 */
public abstract class CacheDao<T extends Bean> implements SmartHomeDao<T> {

    private CacheManager cacheManager;
    Gson gson;

    public CacheManager getCacheManager() {
        return cacheManager;

    }

    public CacheDao(Context ctx) {
        cacheManager=CacheManager.getInstance(ctx);
        gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
              //  .registerTypeAdapter(getEntityClass(),new BeanInstanceCreator<T>())
                .create();
    }

    private Class getEntityClass() {
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) t.getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        String gsonString= cacheManager.findElement(getEntityClass().getName());
        if (gsonString.equals(CacheManager.DEFAULT))
            return Collections.EMPTY_LIST;
        else  {

           return gson.fromJson(gsonString, new TypeListElement<T>(getEntityClass()));
        }
    }
    @Override
    public boolean  createOrUpdate(T obj){

        List<T> element= deleteOrCreate();
        int position=element.indexOf(obj);
        element.remove(obj);
        if (position!=-1)
            element.add(position,obj);
        else
            element.add(obj);
      return   cacheManager.addElement(getEntityClass().getName(), gson.toJson(element));
    }

    public boolean delete(){
       return  cacheManager.removeElement(getEntityClass().getName());
    }
    public void deleteAll(){
        cacheManager.deleteAll();
    }
    @Override
    public T findByPkey(Object key){

        int id=(Integer) key;
        String gsonString= cacheManager.findElement(getEntityClass().getName());
        T obj=null;
        List<T>element = gson.fromJson(gsonString, new TypeListElement<T>(getEntityClass()));

        int len=element.size();
        int  taille=0;
        boolean bool=true;
        while (taille <len && bool ){
            obj=element.get(taille);
            if (obj.getId()==id)
                bool=false;
            taille++;
        }
        return obj;
    }

    public List<T> findAllByForeignKey(int id ,String foreignKey) {
        List<T> elements=findAll();

        if (elements.size()>0){

            List<T> results=new ArrayList<>();
            for (int i=0,len=elements.size();i<len;i++){
                if (elements.get(i).getForeignKey(foreignKey)!=null && elements.get(i).getForeignKey(foreignKey).getId()==id)
                    results.add(elements.get(i));
            }
            return results;
        }
        else return Collections.EMPTY_LIST;
    }
    public boolean  addElements(List<T> lists){
       delete();
        return   cacheManager.addElement(getEntityClass().getName(), gson.toJson(lists));


    }
    @Override
    public boolean delete(T obj) {
        List<T> element= deleteOrCreate();
        element.remove(element.indexOf(obj));
        return   cacheManager.addElement(getEntityClass().getName(), gson.toJson(element));
    }
    private List<T>deleteOrCreate(){
        String gsonString= cacheManager.findElement(getEntityClass().getName());
        List<T> element;
        if (gsonString.equals(CacheManager.DEFAULT)) {

            element=new ArrayList<T>();

        }else {
            element = gson.fromJson(gsonString, new TypeListElement<T>(getEntityClass()));

        }
        cacheManager.removeElement(getEntityClass().getName());
        return element;

    }


}

