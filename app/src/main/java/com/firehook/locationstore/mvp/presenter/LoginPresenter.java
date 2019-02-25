package com.firehook.locationstore.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firehook.locationstore.mvp.view.LoginView;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public void showMainScreen() {
        getViewState().showMainScreen();
    }
}
