package com.firehook.locationstore.injection.component;

import com.firehook.locationstore.injection.module.ApplicationModule;
import com.firehook.locationstore.mvp.presenter.LocationsListPresenter;
import com.firehook.locationstore.mvp.presenter.LoginPresenter;
import com.firehook.locationstore.mvp.presenter.LogoutPresenter;
import com.firehook.locationstore.mvp.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent  {
    void inject(MainPresenter presenter);
    void inject(LoginPresenter presenter);
    void inject(LogoutPresenter logoutPresenter);
    void inject(LocationsListPresenter locationsListPresenter);
}
