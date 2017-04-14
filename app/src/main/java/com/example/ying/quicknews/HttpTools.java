package com.example.ying.quicknews;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Created by ying on 2017/2/26.
 * A class for providing all the HTTP tools including checking internet permission, and download from internet
 */

public class HttpTools{

    /**
     * provide string url and return the content of the url
     * @param url - url
     * @return - String - url content
     */
    public static String getNewsJasonString(String url){
        InputStream is = null;
        try {
            is = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isr;
        String jasonFile = "";
        String line = "";
        try {
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while(((line = br.readLine()) != null)){
                jasonFile += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Json",jasonFile);
        return jasonFile;
    }

    /**
     * Checking if there is internet
     * @param context
     * @return true if there is internet access
     */
    public static boolean hasInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork !=  null;
    }

}
