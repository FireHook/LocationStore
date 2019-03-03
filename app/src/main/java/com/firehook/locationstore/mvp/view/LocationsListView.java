package com.firehook.locationstore.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.firehook.locationstore.mvp.model.DeleteMarker;
import com.firehook.locationstore.mvp.model.Location;

import java.util.List;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public interface LocationsListView extends MvpView {
    void initRecyclerAdapter(List<Location> locationList);
    void showDatabseReadingError();
    void showLocationOnMapScreen(Location location);
    void removeMarkerFromMap(DeleteMarker deleteMarker);
}
