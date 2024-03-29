package com.lexivip.lexi.selectionGoodsCenter
import android.content.Intent
import android.support.v4.view.ViewPager
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import com.lexivip.lexi.search.SearchActivity
import kotlinx.android.synthetic.main.activity_selection_goods_center.*


class SelectionGoodsCenterActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_selection_goods_center

    private val fragment0: BaseFragment by lazy { FragmentGoodsRecommend.newInstance() }
    private val fragment1: BaseFragment by lazy { AllGoodsFragment.newInstance() }

    private val fragments:ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }


    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_selection_goods_center)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_selection_goods_titles)

        fragments.add(fragment0)
        fragments.add(fragment1)

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
        slidingTabLayout.getTitleView(0).textSize = 20f
    }

    override fun installListener() {

        includeSearchBox.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }

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
