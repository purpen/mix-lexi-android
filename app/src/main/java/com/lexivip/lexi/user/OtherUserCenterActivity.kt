package com.lexivip.lexi.user

import android.content.Intent
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.coupon.UserCouponActivity
import com.lexivip.lexi.mine.MineContract
import com.lexivip.lexi.mine.MineFavoritesAdapter
import com.lexivip.lexi.mine.MinePresenter
import com.lexivip.lexi.mine.UserCenterBean
import com.lexivip.lexi.mine.designPavilion.FavoriteShopFragment
import com.lexivip.lexi.mine.dynamic.DynamicActivity
import com.lexivip.lexi.mine.enshrine.EnshrineFragment
import com.lexivip.lexi.mine.like.FavoriteFragment
import com.lexivip.lexi.orderList.OrderListActivity
import com.lexivip.lexi.user.setting.userData.EditUserDataActivity
import kotlinx.android.synthetic.main.activity_other_user_center.*
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*

class OtherUserCenterActivity:BaseActivity(),MineContract.View,View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val presenter: MinePresenter by lazy { MinePresenter(this) }
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>
    override val layout: Int = R.layout.activity_other_user_center
    private lateinit var userId:String

    override fun getIntentData() {
        userId = intent.getStringExtra(TAG)
    }

    override fun initView() {
        customHeadView.setRightImgBtnShow(true)
        setUpViewPager()
        adapter0 = MineFavoritesAdapter(R.layout.adapter_goods_layout)
    }


    private fun setUpViewPager() {
        fragments = ArrayList()
        fragments.add(FavoriteFragment.newInstance(userId))
        fragments.add(EnshrineFragment.newInstance(userId))
        fragments.add(FavoriteShopFragment.newInstance(userId))

        val titles = resources.getStringArray(R.array.strings_mine_titles)

        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.setPagingEnabled(false)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
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
            startActivity(Intent(this, DynamicActivity::class.java))
        }

        imageView.setOnClickListener {
            startActivity(Intent(this, EditUserDataActivity::class.java))
        }

        textViewName.setOnClickListener {
            startActivity(Intent(this, EditUserDataActivity::class.java))
        }

        //优惠券
        linearLayoutCoupon.setOnClickListener {
            startActivity(Intent(this, UserCouponActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imageViewShare -> {

            }
            R.id.buttonOrder->startActivity(Intent(this, OrderListActivity::class.java))
        }
    }


    override fun requestNet() {
        presenter.loadData(userId)
    }
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
        GlideUtil.loadCircleImageWidthDimen(data.avatar, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp70),ImageSizeConfig.SIZE_AVA)
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