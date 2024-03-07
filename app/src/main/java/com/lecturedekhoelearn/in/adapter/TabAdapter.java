package com.lecturedekhoelearn.in.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.lecturedekhoelearn.in.fragment.DocumentFragment;
import com.lecturedekhoelearn.in.fragment.McqFragment;
import com.lecturedekhoelearn.in.fragment.VideoFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new VideoFragment();
        } else if (position == 1) {
            fragment = new DocumentFragment();
        }else if (position == 2) {
            fragment = new McqFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Video";
        } else if (position == 1) {
            title = "Document";
        }else if (position == 2) {
            title = "MCQ";
        }
        return title;
    }
}

