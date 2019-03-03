package com.firehook.locationstore.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.firehook.locationstore.mvp.model.Location;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public interface LocationsMapView extends MvpView {
    void showDatabseReadingError();
    void initMapWithLocations(List<DocumentSnapshot> locationList);
    void showDatabaseInsertingError();
    void moveCameraToMarker(Location location);
    void drawMarker(Location location);
}
