package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.mvp.view.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Inject;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    @Inject LocationStoreManager mLocationStoreManager;

    public LoginPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
    }

    public void saveGoogleClientAccount(GoogleSignInClient client, GoogleSignInAccount account) {
        mLocationStoreManager.setGoogleSignInClient(client);
        mLocationStoreManager.setGoogleSignInAccount(account);
    }

    public void showMainScreen() {
        getViewState().showMainScreen();
    }
}
