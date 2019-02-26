package com.firehook.locationstore.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.firehook.locationstore.R;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class MainFragment extends MvpAppCompatFragment implements MainFragmentView {

    private NonSwipeableViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "Main Fragment!", Toast.LENGTH_LONG).show();
        mViewPager = getActivity().findViewById(R.id.view_pager);
        mTabLayout = getActivity().findViewById(R.id.tab_layout);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int i, float v, int i1) { }
            @Override public void onPageSelected(int i) { }
            @Override public void onPageScrollStateChanged(int i) { }
        });

        mPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new LocationsListFragment(),
                    new LocationsMapFragment(),
                    new LogoutFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.locations_list_fragment),
                    getString(R.string.map_locations_fragment),
                    getString(R.string.log_out_fragment)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
