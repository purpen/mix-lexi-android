package com.thn.lexi.search
import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import com.thn.lexi.index.explore.FragmentExplore
import com.thn.lexi.index.selection.FragmentSelection
import kotlinx.android.synthetic.main.acticity_search.*


class SearchActivity : BaseActivity(){

    private lateinit var adapter:CustomFragmentPagerAdapter

    override val layout: Int = R.layout.acticity_search


    override fun initView() {
        customHeadView.setHeadSearchShow(true)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_search_titles)
        val fragments = ArrayList<BaseFragment>()
        fragments.add(FragmentSearchGoods.newInstance())
        fragments.add(FragmentSearchBrandPavilion.newInstance())
        fragments.add(FragmentSearchUserList.newInstance())

        adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.toMutableList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
        slidingTabLayout.getTitleView(0).textSize = 20f
    }

    override fun installListener() {
        customViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val count = customViewPager.childCount
                for (i in 0 until count) {
                    if (i == position) {
                        slidingTabLayout.getTitleView(i).textSize = 20f
                    } else {
                        slidingTabLayout.getTitleView(i).textSize = 17f
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })
    }

}
