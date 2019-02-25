package com.firehook.locationstore.mvp.view;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MainView extends MvpView {
    void showLoginScreen();
    void showMainScreen(Intent data);
}
