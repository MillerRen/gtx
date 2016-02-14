package com.xxskb.gtx;

import android.app.Application;

/**
 * Created by renyufei on 15-10-3.
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
    }
}
