package com.smarthome.view;
import com.smarthome.beans.House;

import java.util.List;

/**
 * Created by Mdiallo on 26/12/2015.
 */
public interface SpinnerObserver {
   public  void updateSpinner(List<House> houses);
}
