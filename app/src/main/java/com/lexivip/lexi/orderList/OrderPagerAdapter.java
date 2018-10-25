package com.lexivip.lexi.orderList;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.basemodule.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments;
    private List<String> titles;
    public OrderPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments,List<String> titles) {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
