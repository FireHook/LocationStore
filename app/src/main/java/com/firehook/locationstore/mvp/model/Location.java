package com.firehook.locationstore.mvp.model;

import android.support.annotation.Nullable;

import com.google.firebase.firestore.GeoPoint;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class Location {
    public String id;
    public String name;
    public GeoPoint geoPoint;

    public Location() { }

    public Location(DeleteMarker deleteMarker) {
        name = deleteMarker.name;
        geoPoint = deleteMarker.geoPoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + geoPoint.hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (!name.equals(other.name))
            return false;
        if (geoPoint.getLatitude() != other.geoPoint.getLatitude() || geoPoint.getLongitude() != other.geoPoint.getLongitude())
            return false;
        return true;
    }
}
