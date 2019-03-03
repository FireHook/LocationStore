package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.LocationStoreApp;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.mvp.model.DeleteMarker;
import com.firehook.locationstore.mvp.model.Location;
import com.firehook.locationstore.mvp.view.LocationsListView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.firehook.locationstore.util.ConstantsUtil.column_geopoint;
import static com.firehook.locationstore.util.ConstantsUtil.column_name;
import static com.firehook.locationstore.util.ConstantsUtil.database_collection;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LocationsListPresenter extends MvpPresenter<LocationsListView> {

    @Inject LocationStoreManager mLocationStoreManager;

    private FirebaseFirestore mFirestore;

    public FirebaseFirestore getDatabaseInstance() {
        return mFirestore;
    }

    public LocationsListPresenter() {
        LocationStoreApp.sApplicationComponent.inject(this);
        mFirestore = mLocationStoreManager.getFirebaseFirestore();
    }

    public void getDatabaseData() {
        mFirestore.collection(database_collection).orderBy(column_name, Query.Direction.ASCENDING).get()
                .addOnSuccessListener(querySnapshot -> {
                    Timber.d("Database response SUCCESS");
                    List<Location> locationList = new ArrayList<>();
                    for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                        Location location = new Location();
                        location.id = ds.getId();
                        location.name = ds.getString(column_name);
                        location.geoPoint = ds.getGeoPoint(column_geopoint);
                        locationList.add(location);
                    }
                    getViewState().initRecyclerAdapter(locationList);
                })
                .addOnFailureListener(e -> {
                    getViewState().showDatabseReadingError();
                });
    }

    public void deleteRecordFromDB(Location location) {

        DeleteMarker deleteMarker = new DeleteMarker();
        deleteMarker.name = location.name;
        deleteMarker.geoPoint = location.geoPoint;

        mFirestore.collection(database_collection).document(location.id).delete()
                .addOnSuccessListener(aVoid ->  {
                    Timber.d("Database record successfully deleted, data %s", deleteMarker.name);
                    getViewState().removeMarkerFromMap(deleteMarker);
                })
                .addOnFailureListener(e -> {
                    Timber.d("Database record deleting error");
                });
    }

    public void showMapScreen(Location location) {
        getViewState().showLocationOnMapScreen(location);
    }
}