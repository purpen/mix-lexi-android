package com.lexivip.lexi.index.shopWindow
import android.content.Intent
import android.support.v4.view.ViewPager
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.FragmentFocusShowWindow
import com.lexivip.lexi.discoverLifeAesthetics.FragmentRecommendShowWindow
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_shop_window.*

class FragmentShopWindow : BaseFragment() {
    override val layout: Int = R.layout.fragment_shop_window
    private val fragment0: BaseFragment by lazy { FragmentFocusShowWindow.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentRecommendShowWindow.newInstance() }

    private val fragments:ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }

    override fun initView() {
        GlideUtil.loadImageWithDimenAndRadius(R.mipmap.icon_bg_header_shop_window,imageViewBg,0,ScreenUtil.getScreenWidth(),DimenUtil.dp2px(255.0))
        setUpViewPager()
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentShopWindow = FragmentShopWindow()
    }

    private fun setUpViewPager() {
        val titles = ArrayList<String>()
        if (UserProfileUtil.isLogin()){
            fragments.add(fragment0)
            titles.add(getString(R.string.text_focus))
        }
        titles.add(getString(R.string.text_recommend))
        fragments.add(fragment1)

        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
    }

    override fun installListener() {

        linearLayoutPublishWindow.setOnClickListener { //跳转拼接橱窗
            startActivity(Intent(activity,PublishShopWindowActivity::class.java))
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