package com.lexivip.lexi.index.shopWindow
import android.content.Intent
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.CustomRefreshHeader
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.FragmentFocusShowWindow
import com.lexivip.lexi.discoverLifeAesthetics.FragmentRecommendShowWindow
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_shop_window.*

class FragmentShopWindow : BaseFragment() {
    override val layout: Int = R.layout.fragment_shop_window
    private val fragment0: BaseFragment by lazy { FragmentFocusShowWindow.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentRecommendShowWindow.newInstance() }

    private val fragments: ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }
    private var isFirstLoad = true
    override fun initView() {
        GlideUtil.loadImageWithDimenAndRadius(R.mipmap.icon_bg_header_shop_window, imageViewBg, 0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(248.0), ImageSizeConfig.DEFAULT)
        appBarLayout.post {
            val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior
            behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentShopWindow = FragmentShopWindow()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && isFirstLoad) {
            setUpViewPager()
            isFirstLoad = false
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    private fun setUpViewPager() {
        val titles = ArrayList<String>()
        if (UserProfileUtil.isLogin()) {
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
        refreshLayout.setRefreshHeader(CustomRefreshHeader(AppApplication.getContext()))
        refreshLayout.isEnableOverScrollBounce = false
        refreshLayout.setEnableOverScrollDrag(false)
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setOnRefreshListener {
            val fragment = fragments[customViewPager.currentItem]
            if (fragment is FragmentFocusShowWindow){
                fragment.refreshData()
            }else if (fragment is FragmentRecommendShowWindow){
                fragment.refreshData()
            }
            refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
        }

        linearLayoutPublishWindow.setOnClickListener {
            //跳转拼接橱窗
            if (UserProfileUtil.isLogin()) {
                startActivity(Intent(activity, PublishShopWindowActivity::class.java))
            }else{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
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