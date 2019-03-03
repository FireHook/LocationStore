package com.firehook.locationstore.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;
    private OnLongItemClickListener mLongItemClickListener;
    private List<Location> mLocationsList;

    public LocationsListAdapter(List<Location> locationList, OnItemClickListener clickListener, OnLongItemClickListener longClickListener) {
        mLocationsList = new ArrayList<>(locationList);
        sortList();
        mItemClickListener = clickListener;
        mLongItemClickListener = longClickListener;
        onDataChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Location location = mLocationsList.get(position);
        viewHolder.mName.setText(location.name);
        viewHolder.mLatitude.setText(String.valueOf(location.geoPoint.getLatitude()));
        viewHolder.mLongitude.setText(String.valueOf(location.geoPoint.getLongitude()));

        viewHolder.mImageView.setOnClickListener(v -> {
            if (mItemClickListener != null) mItemClickListener.onItemClicked(location);
        });

        viewHolder.itemView.setOnLongClickListener(v -> {
            if (mLongItemClickListener != null) {
                mLongItemClickListener.onLongItemClicked(location);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mLocationsList.size();
    }

    public void onDocumentAdded(Location location) {
        mLocationsList.add(location);
        sortList();
        notifyDataSetChanged();
        onDataChanged();
    }

    public void onDocumentRemoved(Location location) {
        mLocationsList.remove(location);
        sortList();
        notifyDataSetChanged();
        onDataChanged();
    }

    private void sortList() {
        Collections.sort(mLocationsList, (o1, o2) -> o1.name.compareTo(o2.name));
    }

    protected void onDataChanged() {}

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name) TextView mName;
        @BindView(R.id.text_latitude) TextView mLatitude;
        @BindView(R.id.text_longitude) TextView mLongitude;
        @BindView(R.id.iamge_view) ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Location location);
    }

    public interface OnLongItemClickListener {
        void onLongItemClicked(Location location);
    }
}
