package com.firehook.locationstore.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firehook.locationstore.R;
import com.firehook.locationstore.mvp.presenter.MainPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.showLoginScreen();
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
//            super.onBackPressed();
//        else
//            finish();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginFragment.RC_SIGN_IN) {
            mPresenter.showMainScreen(data);
        }
    }

    @Override
    public void showLoginScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LoginFragment(), LoginFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void showMainScreen(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        Toast.makeText(getBaseContext(), "Entered:" + task.getResult().getDisplayName(), Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), MainFragment.class.getSimpleName())
                .commit();
    }
}
