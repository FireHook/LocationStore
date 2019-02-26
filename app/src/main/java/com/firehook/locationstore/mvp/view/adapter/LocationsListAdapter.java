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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationsListAdapter extends FirestoreAdapter<LocationsListAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;

    public LocationsListAdapter(Query query, OnItemClickListener listener) {
        super(query);
        mItemClickListener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DocumentSnapshot snapshot = getSnapshot(position);
        Location location = snapshot.toObject(Location.class);
        viewHolder.mName.setText(location.name);
        viewHolder.mLatitude.setText(String.valueOf(location.latitude));
        viewHolder.mLongitude.setText(String.valueOf(location.longitude));

        viewHolder.mImageView.setOnClickListener(v -> {
            if (mItemClickListener != null) mItemClickListener.onItemClicked(location);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mLongitude;
        TextView mLatitude;
        ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.text_name);
            mLatitude = itemView.findViewById(R.id.text_latitude);
            mLongitude = itemView.findViewById(R.id.text_longitude);
            mImageView = itemView.findViewById(R.id.iamge_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Location location);
    }
}
