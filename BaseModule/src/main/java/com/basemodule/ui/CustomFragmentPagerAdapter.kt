package com.basemodule.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author lilin
 * created at 2016/8/8 13:03
 */
class CustomFragmentPagerAdapter(fm: FragmentManager, private var classes: ArrayList<BaseFragment>, private var titles: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
           return classes[position]
    }

    override fun getCount(): Int {
        return classes.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}
