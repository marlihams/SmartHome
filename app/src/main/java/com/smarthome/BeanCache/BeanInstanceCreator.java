package com.smarthome.BeanCache;

import com.google.gson.InstanceCreator;
import com.smarthome.beans.Bean;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Mdiallo on 27/12/2015.
 */
public class BeanInstanceCreator<T extends Bean> implements InstanceCreator<T> {


    @Override
    public T createInstance(Type type) {

        return null;
    }
}
