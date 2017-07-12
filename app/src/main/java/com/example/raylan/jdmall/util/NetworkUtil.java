package com.example.raylan.jdmall.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Raylan on 2017/7/9.
 */

public class NetworkUtil {

    public static String deGet(String urlPath){
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() ==200 ){
                InputStream is = conn.getInputStream();
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                return buf.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String doPost(String urlPath, HashMap<String,String> params){
        //HttpURLConnection
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

//          --------------用来构建数据-------------------------------------------------
            String paramStr="";  //数据从params中来
            Set<HashMap.Entry<String,String>> entrySet = params.entrySet();
            for (HashMap.Entry<String,String> entry : entrySet) {
                paramStr += "&"+entry.getKey()+"="+entry.getValue();
            }
            paramStr = paramStr.substring(1);
//          ------------------------------------------------------------------------

            conn.setDoOutput(true);  //z打开输出流的开关
            conn.getOutputStream().write(paramStr.getBytes());
            if (conn.getResponseCode() ==200 ){
                InputStream is = conn.getInputStream();
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                return buf.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
