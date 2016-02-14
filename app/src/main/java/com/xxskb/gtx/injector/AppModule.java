package com.xxskb.gtx.injector;

import android.content.Context;

import com.xxskb.gtx.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renyufei on 16-2-14.
 */

@Module
public class AppModule {
    private final BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    protected BaseApplication provideApplication(){
        return this.mApplication;
    }

    protected Context provideContext(){
        return mApplication;
    }
}
