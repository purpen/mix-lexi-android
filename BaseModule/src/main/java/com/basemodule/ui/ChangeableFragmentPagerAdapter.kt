package com.basemodule.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

class ChangeableFragmentPagerAdapter(fm: FragmentManager, private var classes: ArrayList<BaseFragment>, private var titles: List<String>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return classes[position]
    }

    override fun getCount(): Int {
        return classes.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
    override fun getItemPosition(`object`: Any): Int {
        return FragmentStatePagerAdapter.POSITION_NONE
    }

}
