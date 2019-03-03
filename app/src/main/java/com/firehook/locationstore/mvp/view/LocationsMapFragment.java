package com.firehook.locationstore.mvp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.model.DeleteMarker;
import com.firehook.locationstore.mvp.model.Location;
import com.firehook.locationstore.mvp.presenter.LocationMapPresenter;
import com.firehook.locationstore.mvp.rx.RxBus;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.firehook.locationstore.util.ConstantsUtil.column_geopoint;
import static com.firehook.locationstore.util.ConstantsUtil.column_name;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationsMapFragment extends MvpAppCompatFragment implements LocationsMapView, OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    @InjectPresenter LocationMapPresenter mPresenter;

    private GoogleMap mGoogleMap;
    private Disposable mDisposable;
    private Map<Location, Marker> mMarkers;

    private AddLocationDialogFragment.NoticeDialogListener mDialogClickListener = location ->  {
        mPresenter.insertLocation(location);
    };

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);

        mDisposable = RxBus.subscribe(object -> {
            if (object instanceof Location) {
                Location location = (Location) object;
                mPresenter.moveCameraToMarker(location);
            }
            if (object instanceof DeleteMarker) {
                DeleteMarker deleteMarker = (DeleteMarker) object;
                Location location = new Location(deleteMarker);
                if (mMarkers.containsKey(location)) {
                    mMarkers.get(location).remove();
                    mMarkers.remove(location);
                }
            }
        }, throwable -> {
            Timber.d("RxBus Exception: %s", throwable.getMessage());
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Turn ON Location permission for the App", Toast.LENGTH_LONG).show();
            askForGeoPermission();
        }

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(17)
                        .build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        mPresenter.getDatabaseData();
    }

    @Override
    public void initMapWithLocations(List<DocumentSnapshot> locationList) {
        mMarkers = new HashMap<>();

        for (DocumentSnapshot snapshot : locationList) {
            Location location = new Location();
            location.name = snapshot.getString(column_name);
            location.geoPoint = snapshot.getGeoPoint(column_geopoint);
            LatLng latLng = new LatLng(snapshot.getGeoPoint(column_geopoint).getLatitude(), snapshot.getGeoPoint(column_geopoint).getLongitude());
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(location.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plusone_small_off_client)).draggable(true));
            mMarkers.put(location, marker);
        }
    }

    @Override
    public void moveCameraToMarker(Location location) {
        LatLng latLng = new LatLng(location.geoPoint.getLatitude(), location.geoPoint.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (mMarkers.containsKey(location))
            mMarkers.get(location).showInfoWindow();
    }

    @Override
    public void drawMarker(Location location) {
        LatLng latLng = new LatLng(location.geoPoint.getLatitude(), location.geoPoint.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17)
                .build();
        Marker marker = (mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(location.name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plusone_small_off_client)).draggable(true)));
        marker.showInfoWindow();
        mMarkers.put(location, marker);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        AddLocationDialogFragment dialog = AddLocationDialogFragment.newInstance(latLng);
        dialog.setNoticeDialogListener(mDialogClickListener);
        dialog.show(getChildFragmentManager(), AddLocationDialogFragment.class.getSimpleName());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.isInfoWindowShown()) {
            marker.showInfoWindow();
            return true;
        } else{
            marker.hideInfoWindow();
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    @Override
    public void showDatabaseInsertingError() { Toast.makeText(getContext(), "@strings/firebase_error", Toast.LENGTH_SHORT).show(); }

    @Override
    public void showDatabseReadingError() { Toast.makeText(getContext(), "@strings/firebase_error", Toast.LENGTH_SHORT).show(); }

    private void askForGeoPermission(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("");
        alertBuilder.setMessage("GPS is off. Turn ON to continue");
        alertBuilder.setPositiveButton("Yes",(dialog, which) ->  {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
