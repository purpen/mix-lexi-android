package com.lexivip.lexi

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.mine.*
import com.lexivip.lexi.mine.designPavilion.FavoriteShopFragment
import com.lexivip.lexi.mine.dynamic.DynamicActivity
import com.lexivip.lexi.mine.enshrine.EnshrineFragment
import com.lexivip.lexi.mine.like.FavoriteFragment
import com.lexivip.lexi.orderList.OrderListActivity
import com.lexivip.lexi.user.setting.SettingActivity
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*
import android.Manifest.permission
import android.Manifest.permission.WRITE_APN_SETTINGS
import android.Manifest.permission.GET_ACCOUNTS
import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.Manifest.permission.SET_DEBUG_APP
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.READ_LOGS
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import com.umeng.commonsdk.UMConfigure
import com.lexivip.lexi.coupon.UserCouponActivity
import com.lexivip.lexi.user.setting.userData.EditUserDataActivity


class MainFragment3 : BaseFragment(), MineContract.View, View.OnClickListener {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: MinePresenter by lazy { MinePresenter(this) }
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>

    companion object {
        fun newInstance(bean: UserCenterBean.DataBean): MainFragment3 {
            val mainFragment3 = MainFragment3()
            val bundle = Bundle()
            bundle.putParcelable("key", bean)
            mainFragment3.arguments = bundle
            return mainFragment3
        }


        fun newInstance(): MainFragment3 {
            val mainFragment3 = MainFragment3()
            return mainFragment3
        }
    }


    override val layout: Int = R.layout.fragment_main3


    private fun setUpViewPager() {
        fragments = ArrayList()
        fragments.add(FavoriteFragment.newInstance())
        fragments.add(EnshrineFragment.newInstance())
        fragments.add(FavoriteShopFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_mine_titles)

        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles.asList())
        customViewPager.setPagingEnabled(false)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
    }


    override fun initView() {
        setUpViewPager()
        adapter0 = MineFavoritesAdapter(R.layout.adapter_goods_layout)
    }


    /**
     * 重置文字颜色
     */
    private fun resetTextColor() {
        val color = Util.getColor(R.color.color_999)
        textViewLikeNum.setTextColor(color)
        textViewLike.setTextColor(color)
        textViewEnshrineNum.setTextColor(color)
        textViewEnshrine.setTextColor(color)
        textViewDesignNum.setTextColor(color)
        textViewDesign.setTextColor(color)
    }

    override fun installListener() {
        customViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewLikeNum.setTextColor(color)
                        textViewLike.setTextColor(color)
                    }

                    1 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewEnshrineNum.setTextColor(color)
                        textViewEnshrine.setTextColor(color)
                    }

                    2 -> {
                        val color = Util.getColor(R.color.color_6ed7af)
                        resetTextColor()
                        textViewDesignNum.setTextColor(color)
                        textViewDesign.setTextColor(color)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })

        imageViewShare.setOnClickListener(this)
        imageViewSetting.setOnClickListener(this)
        buttonOrder.setOnClickListener(this)

        linearLayoutLike.setOnClickListener {
            customViewPager.setCurrentItem(0, true)
        }

        linearLayoutEnshrine.setOnClickListener {
            customViewPager.setCurrentItem(1, true)
        }

        linearLayoutDesign.setOnClickListener {
            customViewPager.setCurrentItem(2, true)
        }

        buttonActivity.setOnClickListener {
            startActivity(Intent(activity, DynamicActivity::class.java))
        }

        imageView.setOnClickListener {
            startActivity(Intent(activity,EditUserDataActivity::class.java))
        }

        textViewName.setOnClickListener {
            startActivity(Intent(activity,EditUserDataActivity::class.java))
        }

        //优惠券
        linearLayoutCoupon.setOnClickListener {
            startActivity(Intent(activity, UserCouponActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imageViewShare -> {
                UMConfigure.setLogEnabled(true)
                //分享
                val mPermissionList = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS)
                ShareAction(activity).withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(object :UMShareListener{
                            override fun onResult(p0: SHARE_MEDIA?) {

                            }

                            override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                                LogUtil.e("回调出错")
                            }

                            override fun onStart(p0: SHARE_MEDIA?) {

                            }

                            override fun onCancel(p0: SHARE_MEDIA?) {

                            }

                        }).open()
            }
            R.id.imageViewSetting -> startActivity(Intent(activity, SettingActivity::class.java))
            R.id.buttonOrder->startActivity(Intent(activity,OrderListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

//    override fun loadData() {
//        presenter.loadData()
//    }

    /**
     * 设置用户数据
     */
    override fun setUserData(data: UserCenterBean.DataBean) {
        textViewLikeNum.text = data.user_like_counts
        textViewEnshrineNum.text = data.wish_list_counts
        textViewDesignNum.text = data.followed_stores_counts
        textViewFocusNum.text = data.followed_users_counts
        textViewFansNum.text = data.fans_counts
        textViewName.text = data.username
        if (TextUtils.isEmpty(data.about_me)) {
            textViewSignature.visibility = View.GONE
        } else {
            textViewSignature.visibility = View.VISIBLE
            textViewSignature.text = data.about_me
        }
        GlideUtil.loadCircleImageWidthDimen(data.avatar, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp70))
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    override fun setPresenter(presenter: MineContract.Presenter?) {
        setPresenter(presenter)
    }
}