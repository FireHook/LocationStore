package com.firehook.locationstore.mvp.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.model.Location;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

/**
 * Created by Vladyslav Bondar on 02.03.2019
 * Skype: diginital
 */

public class AddLocationDialogFragment extends DialogFragment {

    private NoticeDialogListener mNoticeDialogListener;
    private LatLng mLatLng;

    public static AddLocationDialogFragment newInstance(LatLng latLng) {
        AddLocationDialogFragment instance = new AddLocationDialogFragment();
        instance.mLatLng = latLng;
        return instance;
    }

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_dialog_add_location, null))
                .setPositiveButton(R.string.ok, (dialog, which) ->  {
                    Location location = new Location();
                    location.name = String.valueOf(((EditText) getDialog().findViewById(R.id.edit_name)).getText());
                    location.geoPoint = new GeoPoint(mLatLng.latitude, mLatLng.longitude);
                    if (mNoticeDialogListener != null)
                        mNoticeDialogListener.onDialogPositiveClick(location);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) ->  {
                    dialog.cancel();
                });

        return builder.create();
    }

    public void setNoticeDialogListener(NoticeDialogListener noticeDialogListener) {
        mNoticeDialogListener = noticeDialogListener;
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Location location);
    }
}
