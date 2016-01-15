package com.smarthome.model;

import android.graphics.Color;

import com.smarthome.BeanCache.DeviceCacheDao;
import com.smarthome.BeanCache.HistoriqueCacheDao;
import com.smarthome.android.DeviceDetailActivity;
import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.view.DeviceObserver;
import com.smarthome.vo.ConsoVO;
import com.smarthome.vo.PieChartConsoVO;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
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
        historiques=historiqueCacheDao.findAllByForeignKey(deviceId,"device_id");
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
    public List<PieChartConsoVO> getDeviceConsoVo() {

        List<PieChartConsoVO>consoVOs=new ArrayList<>();

       double consoTotal=0.0;
        double conso;
        double inter=0.0;
    List<Historique> all=getLastConsumptionsByDevice();
        consoVOs.add(new PieChartConsoVO(all.get(0).getPeriode(),device.getName(),0.0,Color.RED));
        consoVOs.add(new PieChartConsoVO(all.get(0).getPeriode(),"others device ", 0.0,Color.BLUE));
        for (Historique histo : all){
            consoTotal+=histo.getConsommation();
        }
        for (Historique histo : all) {
            conso = histo.getConsommation() / consoTotal;
            if (histo.getDevice().getId() == device.getId()) {
                consoVOs.get(0).setConsommation(conso);
            } else {
                inter += conso;
            }
        }
            consoVOs.get(1).setConsommation(inter);
        return consoVOs;
    }


    @Override
    public List<Historique> getLastConsumptionsByDevice() {
        List<Historique> lastConsumptionByDevice = new ArrayList<Historique>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");
        //récupérer la dernière période pendant laquelle il y a eu des consommations
        List<Historique> all = historiqueCacheDao.findAll();
        DateTime lastPeriod = formatter.parseDateTime(all.get(0).getPeriode());
        for(int i = 1,len=all.size(); i < len; i++) {
            if(formatter.parseDateTime(all.get(i).getPeriode()).isBefore(lastPeriod)) {
                lastPeriod = formatter.parseDateTime(all.get(i).getPeriode());
            }
        }

        for(Historique historique :all) {
            if(historique.getDevice() != null && historique.getPeriode().equals(formatter.print(lastPeriod))){
                lastConsumptionByDevice.add(historique);
            }
        }
        return lastConsumptionByDevice;
    }
}
