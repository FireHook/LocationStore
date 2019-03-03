package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.model.DeleteMarker;
import com.firehook.locationstore.mvp.model.Location;
import com.firehook.locationstore.mvp.presenter.LocationsListPresenter;
import com.firehook.locationstore.mvp.rx.RxBus;
import com.firehook.locationstore.mvp.view.adapter.LocationsListAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.firehook.locationstore.util.ConstantsUtil.column_geopoint;
import static com.firehook.locationstore.util.ConstantsUtil.column_name;
import static com.firehook.locationstore.util.ConstantsUtil.database_collection;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationsListFragment extends MvpAppCompatFragment implements LocationsListView, LocationsListAdapter.OnItemClickListener,
        LocationsListAdapter.OnLongItemClickListener {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.text_empty) TextView mEmptyView;

    @InjectPresenter LocationsListPresenter mPresenter;

    private LocationsListAdapter mAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getDatabaseData();

        mPresenter.getDatabaseInstance().collection(database_collection)
            .addSnapshotListener((querySnapshot, e) -> {
                if (e != null) {
                    Timber.d("Snapshot listener error: %s", e.getCode());
                    return;
                }

                for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                    DocumentSnapshot snapshot = change.getDocument();
                    Location location = new Location();
                    location.id = snapshot.getId();
                    location.name = snapshot.getString(column_name);
                    location.geoPoint = snapshot.getGeoPoint(column_geopoint);

                    if (change.getType() == DocumentChange.Type.ADDED) {
                        if (mAdapter != null) {
                            mAdapter.onDocumentAdded(location);
                        }
                    }
                    if (change.getType() == DocumentChange.Type.REMOVED) {
                        if (mAdapter != null) {
                            mAdapter.onDocumentRemoved(location);
                        }
                    }
                }
            });
    }

    @Override
    public void onItemClicked(Location location) {
        mPresenter.showMapScreen(location);
    }

    @Override
    public void onLongItemClicked(Location location) {
        new AlertDialog.Builder(getContext())
                .setMessage("Delete location?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mPresenter.deleteRecordFromDB(location);
                }).setNegativeButton("No", (dialog, which) -> {
                    dialog.cancel();
                }).show();
    }

    @Override
    public void initRecyclerAdapter(List<Location> locationList) {
        mAdapter = new LocationsListAdapter(locationList, this, this) {
            @Override
            protected void onDataChanged() {
                if (getItemCount() == 0) {
                    mRecyclerView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showDatabseReadingError() {
        Toast.makeText(getContext(), "@strings/firebase_error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLocationOnMapScreen(Location location) {
        RxBus.publish(location);

        if (getParentFragment() instanceof MainFragment)
            ((MainFragment) getParentFragment()).switchToMapTab();
    }

    @Override
    public void removeMarkerFromMap(DeleteMarker deleteMarker) {
        RxBus.publish(deleteMarker);
    }
}
