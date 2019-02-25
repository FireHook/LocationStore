package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.R;

import javax.inject.Inject;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LogoutFragment extends MvpAppCompatFragment implements LogoutView {

    @Inject LocationStoreManager mLocationStoreManager;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button mLogOutButton;
        mLogOutButton = getActivity().findViewById(R.id.logout_button);
        mLogOutButton.setOnClickListener( v -> {

            mLocationStoreManager.getGoogleSignInClient().signOut().addOnCompleteListener(task -> {
                mLocationStoreManager.clearCachedClientAccount();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new LoginFragment(), LoginFragment.class.getSimpleName())
                        .commit();
            });
        });
    }

}
