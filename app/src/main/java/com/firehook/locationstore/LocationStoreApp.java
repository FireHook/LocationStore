package com.firehook.locationstore;

import android.app.Application;

import com.firehook.locationstore.injection.component.ApplicationComponent;
import com.firehook.locationstore.injection.component.DaggerApplicationComponent;
import com.firehook.locationstore.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationStoreApp extends Application {

    public static ApplicationComponent sApplicationComponent;

    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new MyTimberTree());

        sApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getApplicationContext())).build();
    }
}
