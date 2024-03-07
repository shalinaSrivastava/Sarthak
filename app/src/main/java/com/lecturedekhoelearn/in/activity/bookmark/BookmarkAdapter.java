package com.lecturedekhoelearn.in.activity.bookmark;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.lecturedekhoelearn.in.fragment.DocumentFragment;
import com.lecturedekhoelearn.in.fragment.McqFragment;
import com.lecturedekhoelearn.in.fragment.VideoFragment;

public class BookmarkAdapter extends FragmentStatePagerAdapter {

    public BookmarkAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new BookmarkVideo();
        } else if (position == 1) {
            fragment = new BookmarkTestSeries();
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
            title = "Video";
        } else if (position == 1) {
            title = "Test Series";
        }
        return title;
    }
}

