package com.smarthome.beans;

import java.io.Serializable;

/**
 * Created by Mdiallo on 20/12/2015.
 */
public interface Bean extends Serializable {
    public int getId();
    public void setId(int id);
    public Bean getForeignKey(String foreignKey);
}
