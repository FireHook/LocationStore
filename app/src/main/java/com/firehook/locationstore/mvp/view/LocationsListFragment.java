package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.firehook.locationstore.mvp.model.Location;
import com.firehook.locationstore.mvp.presenter.LocationsListPresenter;
import com.firehook.locationstore.mvp.view.adapter.LocationsListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationsListFragment extends MvpAppCompatFragment implements LocationsListView, LocationsListAdapter.OnItemClickListener {

    @InjectPresenter LocationsListPresenter mPresenter;

    FirebaseFirestore mFirestore;
    Query mQuery;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private LocationsListAdapter mAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locations_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.recycler_view);
        mEmptyView = getActivity().findViewById(R.id.text_empty);
        //mPresenter.getDatabase();

        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("locationsStore");
        mAdapter = new LocationsListAdapter(mQuery, this) {
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

            @Override
            protected void onError(FirebaseFirestoreException e) {
                Toast.makeText(getContext(), "@strings/firebase_error", Toast.LENGTH_SHORT).show();
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) mAdapter.stopListening();
    }

    @Override
    public void onItemClicked(Location location) {
        //TODO Show map
    }
}
