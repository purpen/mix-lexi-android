package com.lexivip.lexi.publishShopWindow

import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.acticity_select_shop_window_goods_image.*

class SelectShopWindowGoodsImageActivity : BaseActivity() {
    override val layout: Int = R.layout.acticity_select_shop_window_goods_image

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_select_goods)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_select_goods_titles)
        val fragments = ArrayList<BaseFragment>()
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_LIKE))
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_WISH_ORDER))
        fragments.add(SelectGoodsFragment.newInstance(SelectGoodsFragment.PAGE_RECENT_LOOK))

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.toMutableList())
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