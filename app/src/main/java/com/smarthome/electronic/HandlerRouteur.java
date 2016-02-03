package com.smarthome.electronic;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Mdiallo on 28/01/2016.
 */
public class HandlerRouteur {
    private static String QUERY="http://";
    private static String SCAN="scan.php";
    private static String PAIR="pair.php";
    private static String  UNPAIR="unpair.php";
    private static String ORDER="order.php";
    private static Context lcontext;


    private String action;
    private ProgressDialog bar;
    private RouteurManagerI  routeurManager;
    private String addressHouse;
    private String addressDevice;
    private String order;


    public HandlerRouteur(Context ctx,String addressHouse) {
        lcontext=ctx;
  //      this.bar = new ProgressDialog(ctx);

        this.addressHouse=addressHouse;
    }


    public void handleHttpResponse(String s) {

    }

    public  void getListDevices(){
        this.bar=ProgressDialog.show(lcontext,"please wait...","contating server distant...",true);
        this.bar.setCancelable(true);
        RouteurManager routeurManager=new RouteurManager(this);
        this.routeurManager=routeurManager;
        String[] params=new String[5];
        params[0]=QUERY+addressHouse+"/"+SCAN;
        params[1]="listDevice";
            routeurManager.execute(params);
    }
    protected    void pairDevice(){

        RouteurManager routeurManager=new RouteurManager(this);
        this.routeurManager=routeurManager;
        String[] params=new String[5];
        params[0]=QUERY+addressHouse+"/"+PAIR;
        params[1]="";
        params[2]="addressMac";
        params[3]=addressDevice;
        routeurManager.execute(params);
    }

    protected  void unpairDevice(){
        RouteurManager routeurManager=new RouteurManager(this);
        this.routeurManager=routeurManager;
        String[] params=new String[5];
        params[0]=QUERY+addressHouse+"/"+UNPAIR;
        params[1]="";
        params[2]="addressMac";
        params[3]=addressDevice;
        routeurManager.execute(params);
    }
    public void orderDevice(){
        RouteurManager routeurManager=new RouteurManager(this);
        this.routeurManager=routeurManager;
        String[] params=new String[5];
        params[0]=QUERY+addressHouse+"/"+ORDER;
        params[1]="";
        params[2]="addressMac";
        params[3]=this.order;
        routeurManager.execute(params);

    }

    public String getAddressHouse() {
        return addressHouse;
    }

    public void setAddressHouse(String addressHouse) {
        this.addressHouse = addressHouse;
    }
    public RouteurManagerI getRouteurManager() {
        return routeurManager;
    }

    public void setRouteurManager(RouteurManagerI routeurManager) {
        this.routeurManager = routeurManager;
    }


    public void ListDeviceRequest() {
//        if (bar.isShowing())
            bar.dismiss();

        String response=routeurManager.getResponse();
        String a="slt";
    //

    }

    public void listDeviceOrder() {

         if (action.equals("order")){

            pairDevice();
            action="pair";
        }
        else if (action.equals("pair")){
            orderDevice();
             action="unpair";
        }
         else if (action.equals("unpair")){
             unpairDevice();
             action=null;
         }
        else {
             action = null;
             if (bar.isShowing())
                 bar.dismiss();
         }
    }
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAddressDevice() {
        return addressDevice;
    }

    public void setAddressDevice(String addressDevice) {
        this.addressDevice = addressDevice;
    }



}
