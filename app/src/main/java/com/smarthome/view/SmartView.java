package com.smarthome.view;

import android.view.View;

/**
 * Created by Mdiallo on 19/12/2015.
 */
public interface SmartView {
    void initializeWidget(View... views);
    void setListener();
    void subscribeObserver();
}
