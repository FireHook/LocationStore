package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.presenter.LogoutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LogoutFragment extends MvpAppCompatFragment implements LogoutView {

    @BindView(R.id.logout_button) Button mLogOutButton;

    @InjectPresenter LogoutPresenter mPresenter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLogOutButton.setOnClickListener( v -> {
            mPresenter.logOut();
        });
    }

    @Override
    public void showLoginScreen() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LoginFragment(), LoginFragment.class.getSimpleName())
                .commit();
    }
}
