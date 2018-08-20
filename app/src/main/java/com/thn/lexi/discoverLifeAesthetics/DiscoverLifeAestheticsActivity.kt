package com.thn.lexi.discoverLifeAesthetics
import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import com.thn.lexi.selectionGoodsCenter.AllGoodsFragment
import kotlinx.android.synthetic.main.activity_discover_life_aesthetics.*


class DiscoverLifeAestheticsActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_discover_life_aesthetics

    private val fragment0: BaseFragment by lazy { FragmentRecommendShowWindow.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentRecommendShowWindow.newInstance() }

    private val fragments:ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }


    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_show_case)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_discover_life_titles)
        fragments.add(fragment0)
        fragments.add(fragment1)

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)

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
