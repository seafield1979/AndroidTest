package com.sunsunsoft.shutaro.testview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by shutaro on 2016/10/19.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyFragment.newInstance(android.R.color.holo_blue_bright);
            case 1:
                return MyFragment2.newInstance(android.R.color.holo_green_light);
            case 2:
                return MyFragment3.newInstance(android.R.color.holo_red_dark);
            case 3:
                return MyFragment4.newInstance(android.R.color.holo_purple);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "ページ" + (position + 1);
    }
}