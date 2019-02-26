package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.mvp.view.LogoutView;

import javax.inject.Inject;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LogoutPresenter extends MvpPresenter<LogoutView> {

    @Inject LocationStoreManager mLocationStoreManager;

    public LogoutPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
    }

    public void logOut() {
        mLocationStoreManager.getGoogleSignInClient().signOut().addOnCompleteListener(task -> {
           mLocationStoreManager.clearCachedClientAccount();
           getViewState().showLoginScreen();
        });
    }
}
