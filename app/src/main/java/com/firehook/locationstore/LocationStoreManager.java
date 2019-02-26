package com.firehook.locationstore;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Singleton;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@Singleton
public class LocationStoreManager {

    private Context mApplicationContext;

    private GoogleSignInClient mGoogleSignInClient = null;
    private GoogleSignInAccount mGoogleSignInAccount = null;

    public LocationStoreManager(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        mGoogleSignInClient = googleSignInClient;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return mGoogleSignInAccount;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    public void clearCachedClientAccount() {
        mGoogleSignInClient = null;
        mGoogleSignInAccount = null;
    }

}
