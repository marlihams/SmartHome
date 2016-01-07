package com.smarthome.beans;

/**
 * Created by Mdiallo on 07/01/2016.
 */
public abstract class BeanAbstract implements Bean {

    @Override
    public boolean equals(Object other) {

        if (other==null || other.getClass() !=getClass()) return false;
        else{
            Bean cpr=(Bean)other;
            if (cpr==this)return true;
            else
                return this.getId()==cpr.getId();
        }
    }
}
