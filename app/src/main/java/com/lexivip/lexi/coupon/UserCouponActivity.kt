package com.lexivip.lexi.coupon

import android.text.TextUtils
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.acticity_user_coupon.*

class UserCouponActivity:BaseActivity() {
    private lateinit var adapter:CustomFragmentPagerAdapter
    override val layout: Int = R.layout.acticity_user_coupon

    private var whichPage:String = ""

    override fun getIntentData() {
        if(intent.hasExtra(TAG)){
            whichPage = intent.getStringExtra(TAG)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_user_coupon)
        val titles = resources.getStringArray(R.array.strings_user_coupon_titles)
        val fragments = ArrayList<BaseFragment>()
        fragments.add(FragmentUserCoupon.newInstance(FragmentUserCoupon.PAGE_BRAND_PAVILION))
        fragments.add(FragmentUserCoupon.newInstance(FragmentUserCoupon.PAGE_LX))
        fragments.add(FragmentUserCoupon.newInstance(FragmentUserCoupon.PAGE_INVALID))

        adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.toMutableList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)

        if (TextUtils.equals(whichPage,FragmentUserCoupon.PAGE_LX)){//显示乐喜券
            slidingTabLayout.currentTab = 1
        }
    }
}