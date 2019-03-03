package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.firehook.locationstore.R;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class MainFragment extends MvpAppCompatFragment implements MainFragmentView {

    private NonSwipeableViewPager mViewPager;
    private FragmentStatePagerAdapter mPagerAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = getActivity().findViewById(R.id.view_pager);
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i1) { /* No need */ }
            @Override public void onPageSelected(int i) {
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(mPagerAdapter.getPageTitle(i));
            }
            @Override public void onPageScrollStateChanged(int i) { /* No need */ }
        });

        mPagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new LocationsListFragment(),
                    new LocationsMapFragment(),
                    new LogoutFragment(),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.locations_list_fragment),
                    getString(R.string.map_locations_fragment),
                    getString(R.string.log_out_fragment)
            };
            @Override public Fragment getItem(int position) { return mFragments[position]; }
            @Override public int getCount() { return mFragments.length; }
            @Override public CharSequence getPageTitle(int position) { return mFragmentNames[position]; }
        };

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(mPagerAdapter.getPageTitle(0));
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void switchToMapTab() {
        mViewPager.setCurrentItem(1);
    }
}
