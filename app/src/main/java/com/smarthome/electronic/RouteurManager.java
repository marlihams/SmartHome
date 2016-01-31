package com.smarthome.electronic;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Amstrong on 12/1/2016.
 */
public class RouteurManager  extends AsyncTask<String,Integer, String> implements  RouteurManagerI{
    private static String PARAMETER="listDevice";
    private String response;
    private HandlerRouteur handlerRouteur;
    private HttpURLConnection aHttpURLConnection;


    public RouteurManager(HandlerRouteur handlerRouteur) {
        super();
        response=null;
        this.handlerRouteur=handlerRouteur;
    }

    /**
     * excecuting a http request in background
     * @param params
     * params[0] :url (http://192.168.43.91/scan.php
     * params[1]: action
     * params[2]:key of the request
     * params[3]:value of the request
     * @return
     */

    @Override
    protected String doInBackground(String... params) {

           if (params[1].equals(PARAMETER)) {
               excecuteAction(params[0], null, null);
               return PARAMETER;
           }
           else {
               excecuteAction(params[0], params[1], params[2]);
               return null;
           }
    }

    @Override
    protected void onPostExecute(String s) {
            String a=this.response;
        if (s==null)
            handlerRouteur.listDeviceOrder();
        else
            handlerRouteur.ListDeviceRequest();
    }

    protected void excecuteAction(String request,String key,String value){
        if (key!=null && value !=null)
            runHttpRequest(request + "?" + key + "=" + value);
        else
            runHttpRequest(request);
    }

    public String getResponse() {
        return response;
    }
    public  void setResponse(String response){
        response=response;
    }
    private void  runHttpRequest(String url){
        System.setProperty("http.keepAlive","false");
        BufferedReader aBufferedInputStream = null;
        try {
            URL  aURL = new URL(url);

    /* Open a connection to that URL. */
            aHttpURLConnection = (HttpURLConnection) aURL.openConnection();
            aHttpURLConnection.setDoOutput(false);
            aHttpURLConnection.setChunkedStreamingMode(0);
            aHttpURLConnection.setRequestMethod("GET");
            aHttpURLConnection.connect();
//            OutputStreamWriter writer=new OutputStreamWriter(aHttpURLConnection.getOutputStream());
//            writer.write("");
//            writer.flush();


    /* Define InputStreams to read from the URLConnection. */
           InputStream is= aHttpURLConnection.getInputStream();
            aBufferedInputStream =new BufferedReader(new InputStreamReader(is, "UTF-8"));

    /* Read bytes to the Buffer until there is nothing more to read(-1) */
            //    ByteArrayBuffer aByteArrayBuffer = new ByteArrayBuffer(50);

            String  current="";
            this.response="";
            Log.d("****RUN******",response);

            while ((current = aBufferedInputStream.readLine())!=null) {
                this.response += current;
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        finally {

         //   try{ writer.close();}catch (Exception e){};
            try{ aBufferedInputStream.close();}catch (Exception e){};
            try{aHttpURLConnection.disconnect();}catch (Exception e){};

        }

    }
}
