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
                return MyFragment11.newInstance(android.R.color.holo_blue_bright);
            case 1:
                return MyFragment10.newInstance(android.R.color.holo_green_light);
            case 2:
                return MyFragment9.newInstance(android.R.color.holo_red_dark);
            case 3:
                return MyFragment8.newInstance(android.R.color.holo_purple);
            case 4:
                return MyFragment7.newInstance(android.R.color.holo_orange_light);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "ページ" + (position + 1);
    }
}