package com.lexivip.lexi.brandPavilion
import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.activity_discover_life_aesthetics.*


class BrandPavilionListActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_brand_pavilion_list

    private val fragment0: BaseFragment by lazy { FragmentFeatureBrandPavilion.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentSelectionBrandPavilion.newInstance() }

    private val fragments:ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }


    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_brand_pavilion)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_brand_pavilion_titles)
        fragments.add(fragment0)
        fragments.add(fragment1)

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
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
