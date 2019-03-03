package com.firehook.locationstore.injection.module;

import com.firehook.locationstore.LocationStoreManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@Module
public class ApplicationModule {

    public ApplicationModule() { }

    @Provides @Singleton
    LocationStoreManager provideLocationStoreManager() { return new LocationStoreManager(); }

}
