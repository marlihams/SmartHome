package com.smarthome.BeanCache;

import android.content.Context;

import com.smarthome.beans.Historique;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 25/12/2015.
 */
public class HistoriqueCacheDao extends CacheDao<Historique> {

    public HistoriqueCacheDao(Context ctx) {
        super(ctx);
    }
//    public List<Historique> getHistoriqueByHouse(int houseId){ //useless already done
//        List<Historique>historique=new ArrayList<Historique>();
//        List<Historique>cache=this.findAll();
//        for (int i=0,len=cache.size();i<len;i++){
//            if (cache.get(i).getHouse() != null && cache.get(i).getHouse().getId()==houseId){
//                historique.add(cache.get(i));
//            }
//        }
//        return  historique;
//    }

}