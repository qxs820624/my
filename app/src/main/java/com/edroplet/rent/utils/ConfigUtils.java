package com.edroplet.rent.utils;

import android.content.Context;

import com.edroplet.rent.beans.WebServiceConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {
    private static WebServiceConfig  webServiceConfig =new WebServiceConfig();
    public static WebServiceConfig readProperties(Context context){
        HashMap<String,String> propertiesMap=new HashMap<>();
        Properties properties=new Properties();
        try {
            //assets目录下的config.properties配置文件
            InputStream inputStream=context.getClassLoader().getResourceAsStream("assets/config.properties");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);

            for(Map.Entry entry: properties.entrySet()){
                Object key=entry.getKey();
                Object value=entry.getValue();
                //把value值转换为utf-8编码的字符串，避免乱码
                value = new String(value.toString().getBytes("UTF-8"));
                propertiesMap.put(key.toString().trim(),value.toString().trim());
            }

            inputStream.close();
            WebServiceConfig.setHost(propertiesMap.get("Host"));
            WebServiceConfig.setZhuantiUrl(propertiesMap.get("ZhuantiUrl"));
            return webServiceConfig;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
