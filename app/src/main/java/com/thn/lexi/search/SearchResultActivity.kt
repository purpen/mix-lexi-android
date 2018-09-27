package com.thn.lexi.search
import android.support.v4.view.ViewPager
import android.text.InputType
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import kotlinx.android.synthetic.main.acticity_search.*


class SearchResultActivity : BaseActivity(){

    private lateinit var adapter:CustomFragmentPagerAdapter

    override val layout: Int = R.layout.acticity_search


    override fun initView() {
        customHeadView.setHeadSearchShow(true)
        customHeadView.editTextSearch.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
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
