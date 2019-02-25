package com.firehook.locationstore.mvp.presenter;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.mvp.view.MainView;

import javax.inject.Inject;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject LocationStoreManager mLocationStoreManager;

    public MainPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
    }

    public void showMainScreen(Intent data) {
        getViewState().showMainScreen(data);
    }

    public void showLoginScreen() {
        getViewState().showLoginScreen();
    }
}
