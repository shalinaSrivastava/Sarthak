package com.lecturedekhoelearn.in.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lecturedekhoelearn.in.activity.SearchFragment.FragTopic;
import com.lecturedekhoelearn.in.activity.SearchFragment.FragVideos;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragTopic();
        } else if (position == 1) {
            fragment = new FragVideos();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Topics";
        } else if (position == 1) {
            title = "Videos";
        }
        return title;
    }
}
