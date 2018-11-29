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
import com.lexivip.lexi.index.detail.FavoriteUserListActivity
import com.lexivip.lexi.mine.MineContract
import com.lexivip.lexi.mine.MineFavoritesAdapter
import com.lexivip.lexi.mine.MinePresenter
import com.lexivip.lexi.mine.UserCenterBean
import com.lexivip.lexi.mine.designPavilion.FavoriteShopFragment
import com.lexivip.lexi.mine.dynamic.DynamicActivity
import com.lexivip.lexi.mine.enshrine.EnshrineFragment
import com.lexivip.lexi.mine.like.FavoriteFragment
import kotlinx.android.synthetic.main.activity_other_user_center.*
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*

class OtherUserCenterActivity : BaseActivity(), MineContract.View, View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val presenter: MinePresenter by lazy { MinePresenter(this) }
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>
    override val layout: Int = R.layout.activity_other_user_center
    private lateinit var userId: String
    private var followedStatus: Int = -1
    override fun getIntentData() {
        userId = intent.getStringExtra(TAG)
    }

    override fun initView() {
        customHeadView.setRightImgBtnShow(true)
        buttonFocus.visibility = View.VISIBLE
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

        buttonOrder.visibility = View.GONE

        buttonFocus.setOnClickListener(this)

        linearLayoutFocus.setOnClickListener(this)
        linearLayoutFans.setOnClickListener(this)

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

        //优惠券
        /*linearLayoutCoupon.setOnClickListener {
            startActivity(Intent(this, UserCouponActivity::class.java))
        }*/
        linearLayoutCoupon.visibility = View.GONE
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imageViewShare -> {

            }
            R.id.buttonFocus -> {
                if (followedStatus == -1) return
                presenter.focusUser(userId, v, followedStatus)
            }
            R.id.linearLayoutFocus -> {
                var intent = Intent(this, FavoriteUserListActivity::class.java)
                intent.putExtra("type", 3)
                intent.putExtra("title", Util.getString(R.string.text_focus))
                intent.putExtra("uid", userId)
                startActivity(intent)
            }
            R.id.linearLayoutFans -> {
                var intent = Intent(this, FavoriteUserListActivity::class.java)
                intent.putExtra("type", 4)
                intent.putExtra("title", Util.getString(R.string.text_fans))
                intent.putExtra("uid", userId)
                startActivity(intent)
            }
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
        setFocusState(data.followed_status)
        if (TextUtils.isEmpty(data.about_me)) {
            textViewSignature.visibility = View.GONE
        } else {
            textViewSignature.visibility = View.VISIBLE
            textViewSignature.text = data.about_me
        }
        GlideUtil.loadCircleImageWidthDimen(data.avatar, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp70), ImageSizeConfig.SIZE_AVA)
    }

    /**
     * 设置关注状态
     */
    override fun setFocusState(followed_status: Int) {
        followedStatus = followed_status
        when (followed_status) {
            0 -> { //未关注
                buttonFocus.compoundDrawablePadding = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                buttonFocus.text = Util.getString(R.string.text_focus)
                buttonFocus.setTextColor(Util.getColor(android.R.color.white))
                buttonFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
                buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_white, R.dimen.dp10, R.dimen.dp10), null, null, null)
            }

            1 -> { //已关注
                buttonFocus.text = Util.getString(R.string.text_focused)
                buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
                buttonFocus.setPadding(DimenUtil.getDimensionPixelSize(R.dimen.dp4), 0, 0, 0)
                buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
                buttonFocus.setCompoundDrawables(null, null, null, null)
            }

            2 -> { //相互关注
                buttonFocus.text = Util.getString(R.string.text_focused_each_other)
                buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
                buttonFocus.setPadding(DimenUtil.getDimensionPixelSize(R.dimen.dp4), 0, 0, 0)
                buttonFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
                buttonFocus.setCompoundDrawables(null, null, null, null)
            }

        }
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