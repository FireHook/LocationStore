package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.mvp.model.Location;
import com.firehook.locationstore.mvp.view.LocationsMapView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.firehook.locationstore.util.ConstantsUtil.column_geopoint;
import static com.firehook.locationstore.util.ConstantsUtil.column_name;
import static com.firehook.locationstore.util.ConstantsUtil.database_collection;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LocationMapPresenter extends MvpPresenter<LocationsMapView> {

    @Inject LocationStoreManager mLocationStoreManager;

    private FirebaseFirestore mFirestore;

    public LocationMapPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
        mFirestore = mLocationStoreManager.getFirebaseFirestore();
    }

    public void getDatabaseData() {
        mFirestore.collection(database_collection).orderBy(column_name, Query.Direction.ASCENDING).get()
                .addOnSuccessListener(querySnapshot -> {
                    getViewState().initMapWithLocations(querySnapshot.getDocuments());
                })
                .addOnFailureListener(e -> {
                    getViewState().showDatabseReadingError();
                });
    }

    public void insertLocation(Location location) {
        Map<String, Object> data = new HashMap<>();
        data.put(column_name, location.name);
        data.put(column_geopoint, location.geoPoint);
        mFirestore.collection(database_collection)
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    getViewState().drawMarker(location);
                })
                .addOnFailureListener(e -> {
                    getViewState().showDatabaseInsertingError();
                });
    }

    public void moveCameraToMarker(Location location) {
        getViewState().moveCameraToMarker(location);
    }
}