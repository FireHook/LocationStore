package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.mvp.view.LocationsListView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LocationsListPresenter extends MvpPresenter<LocationsListView> {

    FirebaseFirestore mFirestore;
    Query mQuery;

    public LocationsListPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
    }

    public void getDatabase() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("locationsStore");
    }
}
