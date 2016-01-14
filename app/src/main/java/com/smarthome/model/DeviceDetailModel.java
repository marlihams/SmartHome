package com.smarthome.model;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.view.DeviceObserver;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public class DeviceDetailModel implements DeviceDetailModelI {

    private Device device;

    private DeviceCacheDao deviceCacheDao;
    private HistoriqueCacheDao historiqueCacheDao;
    private List<DeviceObserver> deviceObservers=new ArrayList<DeviceObserver>();
    private List<Historique> historiques;



    private List<Device> devices;

    public DeviceDetailModel(int deviceId) {
        deviceCacheDao=new DeviceCacheDao(DeviceDetailActivity.getlContext());
        deviceCacheDao=new DeviceCacheDao(DeviceDetailActivity.getlContext());
        historiqueCacheDao=new HistoriqueCacheDao(DeviceDetailActivity.getlContext());
        device=deviceCacheDao.findByPkey((Object) deviceId);
   //     historiques=historiqueCacheDao.getHistoriqueByDevice(deviceId);
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void subscribeDeviceObserver(DeviceObserver observer) {
        this.deviceObservers.add(observer);
    }

    @Override
    public void notifyDeviceObserver() {
        for (int i=0;i<deviceObservers.size();i++){
            deviceObservers.get(i).updateDeviceObserver();
        }
    }

    @Override
    public void updateDevice(String name,String pieceName){
        device.setName(name);
        device.setPieceName(pieceName);
        notifyDeviceObserver();
        deviceCacheDao.createOrUpdate(device);
    }

    @Override
    public List<Historique> getDeviceHistorique() {
        return historiques;
    }



    @Override
    public List<Historique> getLastConsumptionsByDevice() {
        List<Historique> lastConsumptionByDevice = new ArrayList<Historique>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");
        //récupérer la dernière période pendant laquelle il y a eu des consommations
        List<Historique> all = historiqueCacheDao.findAll();
        DateTime lastPeriod = formatter.parseDateTime(all.get(0).getPeriode());
        for(int i = 1; i < all.size(); i++) {
            if(formatter.parseDateTime(all.get(i).getPeriode()).isBefore(lastPeriod)) {
                lastPeriod = formatter.parseDateTime(all.get(i).getPeriode());
            }
        }

        for(Historique historique : historiqueCacheDao.findAll()) {
            if(historique.getDevice() != null && historique.getPeriode().equals(formatter.print(lastPeriod))){
                lastConsumptionByDevice.add(historique);
            }
        }
        return lastConsumptionByDevice;
    }
}
