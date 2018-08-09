package com.thn.lexi

import android.content.Intent
import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.goods.explore.FragmentExplore
import com.thn.lexi.goods.lifehouse.FragmentLifeHouse
import com.thn.lexi.goods.selection.FragmentSelection
import kotlinx.android.synthetic.main.fragment_main0.*

class MainFragment0 : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment0 {
            return MainFragment0()
        }
    }

    override val layout: Int = R.layout.fragment_main0
    private  val fragmentLifeHouse:FragmentLifeHouse by lazy { FragmentLifeHouse.newInstance() }

    override fun initView() {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val fragments = ArrayList<BaseFragment>()

        fragments.add(fragmentLifeHouse)
        fragments.add(FragmentSelection.newInstance())
        fragments.add(FragmentExplore.newInstance())

        val titles = resources.getStringArray(R.array.strings_main_titles)
        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)
//        slidingTabLayout.getTitleView(0).textSize = 19f
//        slidingTabLayout.showMsg(0,3)
//        slidingTabLayout.setMsgMargin(0,-45f,13f)

    }

    override fun installListener() {

        customViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val count = customViewPager.childCount
                for (i in 0 until count) {
                    if (i == position) {
                        slidingTabLayout.getTitleView(i).textSize = 19f
                    } else {
                        slidingTabLayout.getTitleView(i).textSize = 16f
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })
    }

    override fun loadData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragmentLifeHouse.onActivityResult(requestCode,resultCode,data)
    }

}