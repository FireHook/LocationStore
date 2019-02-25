package com.firehook.locationstore.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firehook.locationstore.LocationStoreManager;
import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.presenter.LoginPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LoginFragment extends MvpAppCompatFragment implements LoginView {

    @Inject LocationStoreManager mLocationStoreManager;

    @InjectPresenter LoginPresenter mPresenter;

    public final static int RC_SIGN_IN = 123;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "Login Fragment!", Toast.LENGTH_LONG).show();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mLocationStoreManager.setGoogleSignInClient(googleSignInClient);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        mLocationStoreManager.setGoogleSignInAccount(googleSignInAccount);

        if (googleSignInAccount != null) {
            Timber.d("Already entered G+ account:%s", googleSignInAccount.getEmail());
            mPresenter.showMainScreen();
        } else {
            Timber.d("Not entered G+ account!");
        }

        SignInButton signInButton;
        signInButton = getActivity().findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(v -> {
                    Timber.d("Sign In button Clicked!");
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
                }
        );

    }

    @Override
    public void showMainScreen() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), MainFragment.class.getSimpleName())
                .commit();
    }
}
