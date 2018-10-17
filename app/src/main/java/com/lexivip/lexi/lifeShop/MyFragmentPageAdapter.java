package com.lexivip.lexi.lifeShop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragmentPageAdapter  extends FragmentPagerAdapter {
    private ArrayList<com.basemodule.ui.BaseFragment> list;

    public MyFragmentPageAdapter(FragmentManager fm, ArrayList<com.basemodule.ui.BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
