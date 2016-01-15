package com.smarthome.model;

import com.smarthome.beans.Device;
import com.smarthome.beans.Historique;
import com.smarthome.view.DeviceObserver;
import com.smarthome.vo.ConsoVO;
import com.smarthome.vo.PieChartConsoVO;

import java.util.List;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface DeviceDetailModelI {

    List<Historique> getDeviceHistorique();
    public List<PieChartConsoVO> getDeviceConsoVo();
    public void subscribeDeviceObserver(DeviceObserver observer);
    public void notifyDeviceObserver();
    public void updateDevice(String name,String pieceName);
    public Device getDevice();

    public List<Historique> getLastConsumptionsByDevice();

    
}
