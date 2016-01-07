package com.smarthome.BeanCache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Mdiallo on 26/12/2015.
 */
public class TypeListElement<T> implements ParameterizedType {
    private Class<T> elementsClass;

    public TypeListElement(Class<T> elementsClass) {
        this.elementsClass = elementsClass;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {elementsClass};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }
}
