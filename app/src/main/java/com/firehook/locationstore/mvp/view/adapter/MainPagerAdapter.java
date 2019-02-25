package com.firehook.locationstore.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Object> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<Object> data) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override public Fragment getItem(int i) {
        return null;
    }

    @Override public int getCount() {
        return 0;
    }

    @Override public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
