package com.lexivip.lexi

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.widget.FrameLayout
import com.basemodule.tools.Util
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.index.explore.FragmentExplore
import com.lexivip.lexi.index.lifehouse.FragmentLifeHouse
import com.lexivip.lexi.index.selection.FragmentSelection
import com.lexivip.lexi.index.shopWindow.FragmentShopWindow
import com.lexivip.lexi.search.SearchActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_main0.*
import kotlinx.android.synthetic.main.view_head_search_box.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment0 : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment0 {
            return MainFragment0()
        }
    }

    private lateinit var listTitle: MutableList<String>
    private lateinit var adapter: CustomFragmentPagerAdapter
    private val fragments: ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }

    override val layout: Int = R.layout.fragment_main0
    private val fragmentLifeHouse: FragmentLifeHouse by lazy { FragmentLifeHouse.newInstance() }

    override fun initView() {
        EventBus.getDefault().register(this)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val titles = resources.getStringArray(R.array.strings_main_titles)
        listTitle = titles.toMutableList()

        if (UserProfileUtil.isSmallB()) { //添加生活馆
            fragments.add(fragmentLifeHouse)
            listTitle.add(0, Util.getString(R.string.text_life_pavilion))
        }

        fragments.add(FragmentSelection.newInstance())
        fragments.add(FragmentExplore.newInstance())
        fragments.add(FragmentShopWindow.newInstance())
//        fragments.add(FragmentShopWindowDemo.newInstance())

        adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, listTitle)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)


        if (UserProfileUtil.isSmallB()) {
            slidingTabLayout.currentTab = 1
            slidingTabLayout.getTitleView(1).textSize = 19f
        } else {
            slidingTabLayout.currentTab = 0
            slidingTabLayout.getTitleView(0).textSize = 19f
        }

    }

    /**
     * 去掉生活馆界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun removeLifeHouseFragment(message: String) {
        if (TextUtils.equals(FragmentLifeHouse::class.java.simpleName, message)) {
            //移除fragment
            childFragmentManager.beginTransaction().remove(fragmentLifeHouse).commit()
            fragments.remove(fragmentLifeHouse)
            listTitle.remove(Util.getString(R.string.text_life_pavilion))
            adapter.notifyDataSetChanged()
            slidingTabLayout.notifyDataSetChanged()
        }

    }

    override fun installListener() {
        relativeLayout.setOnClickListener {
            startActivity(Intent(AppApplication.getContext(), SearchActivity::class.java))
        }

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragmentLifeHouse.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private var isAnimmating = false
    private var isShowing = true
    private var relativeLayoutHeight = 0
    private var viewHeight=0

    /**
     * 接受动画消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doAnimation(message: MessageUpDown) {
        if (isAnimmating) return
        if (message.up) {
            if (!isShowing) return
            relativeLayoutHeight = relativeLayout.height
            viewHeight = view!!.height
            if (view != null) view!!.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,  viewHeight+ relativeLayoutHeight)
            val animator = ObjectAnimator.ofFloat(linearLayoutBox, "translationY", 0f, -relativeLayoutHeight.toFloat())
            animator.duration = 300
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    isAnimmating = false
                    animation.removeAllListeners()
                    isShowing = false
                }

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    isAnimmating = true


                }
            })
            animator.start()

        } else {
            if (isShowing) return
            relativeLayoutHeight = relativeLayout.height
            val animator = ObjectAnimator.ofFloat(linearLayoutBox, "translationY", -relativeLayoutHeight.toFloat(), 0f)
            animator.duration = 300
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    isAnimmating = false
                    animation.removeAllListeners()
                    isShowing = true
                    if (view != null) view!!.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,viewHeight)
                }

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    isAnimmating = true
                }
            });
            animator.start();
        }

    }
}