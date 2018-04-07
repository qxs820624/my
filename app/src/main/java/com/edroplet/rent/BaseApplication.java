package com.edroplet.rent;

import android.app.Application;
import android.arch.core.BuildConfig;
import android.util.Log;

import com.edroplet.rent.beans.WebServiceConfig;
import com.edroplet.rent.utils.ConfigUtils;

public class BaseApplication extends Application {
    /**
     * 保存从config.properties文件中的参数配置
     */
    public static WebServiceConfig webServiceConfig=new WebServiceConfig();

    @Override
    public void onCreate() {
        super.onCreate();
        webServiceConfig = ConfigUtils.readProperties(getApplicationContext());
        if (BuildConfig.DEBUG) {
            assert webServiceConfig == null;
        }
        Log.i("BaseApplication", "Host: " + webServiceConfig.getHost());
        Log.i("BaseApplication", "ZhuantiUrl: " + webServiceConfig.getZhuantiUrl());
    }
}
