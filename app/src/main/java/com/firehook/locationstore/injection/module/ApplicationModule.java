package com.firehook.locationstore.injection.module;

import android.content.Context;

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

    private Context mContext;

    public ApplicationModule(Context context) { this.mContext = context; }

    @Provides @Singleton
    Context provideContext() { return mContext; }

    @Provides @Singleton
    LocationStoreManager provideLocationStoreManager() { return new LocationStoreManager(mContext); }

}
