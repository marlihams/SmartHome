package com.smarthome.android;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.smarthome.R;

/**
 * Created by Mdiallo on 03/01/2016.
 */
public   class SmartAnimation {

    private static Context ctx;
    public static  Animation fad_in;
    public static  Animation shake;
    public static Animation hyperspace_out;
    public static Animation wave_scale;
    public static  void  init(Context ct){
        ctx=ct;
        setStaticVariable();
    }
    private  static void setStaticVariable(){
    fad_in = AnimationUtils.loadAnimation(ctx, R.anim.fad_in);
     shake=AnimationUtils.loadAnimation(ctx, R.anim.shake);
        hyperspace_out=AnimationUtils.loadAnimation(ctx, R.anim.hyperspace_out);
        wave_scale=AnimationUtils.loadAnimation(ctx, R.anim.wave_scale);
    }
}
