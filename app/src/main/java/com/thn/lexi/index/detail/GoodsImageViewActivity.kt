package com.thn.lexi.index.detail

import android.content.Intent
import android.support.v4.view.ViewPager
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import com.thn.lexi.user.login.LoginActivity
import com.thn.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.activity_goods_image_view.*

class GoodsImageViewActivity : BaseActivity(), GoodsDetailContract.View {

    override val layout: Int = R.layout.activity_goods_image_view

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: GoodsDetailPresenter by lazy { GoodsDetailPresenter(this) }

    private lateinit var goodsData: GoodsAllDetailBean.DataBean

    override fun getIntentData() {
        goodsData = intent.getParcelableExtra(TAG)
    }

    override fun initView() {
        viewPager.adapter = GoodsImagePagerAdapter(goodsData, presenter)
        viewPager.setCurrentItem(goodsData.clickPosition, true)
        textViewNum.text = ("${goodsData.clickPosition + 1} / ${goodsData.assets.size}")

        //设置初始喜欢状态
        if (goodsData.is_like) {
            buttonLike.setBackgroundResource(R.drawable.border_round_2d343a)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            buttonLike.setPadding(DimenUtil.dp2px(2.0), 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_already_like)
        } else {
            buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            buttonLike.compoundDrawablePadding = DimenUtil.dp2px(5.0)
            buttonLike.text = Util.getString(R.string.text_like)
        }

    }

    override fun installListener() {
        imageViewBack.setOnClickListener { finish() }
        val imageCount = goodsData.assets.size
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                textViewNum.text = ("${position + 1} / $imageCount")
            }
        })

        //喜欢
        buttonLike.setOnClickListener { v ->
            if (UserProfileUtil.isLogin()) {
                if (goodsData.is_like) {
                    presenter.favoriteGoods(goodsData.rid, v, false)
                } else {
                    presenter.favoriteGoods(goodsData.rid, v, true)
                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        buttonPurchase.setOnClickListener {
            if (UserProfileUtil.isLogin()) {
                if (goodsData.allSKUData == null) return@setOnClickListener
                val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData, R.id.buttonGoOrderConfirm, goodsData.allSKUData)
                selectSpecificationBottomDialog.show()
            } else {//跳转注册
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        linearLayoutShare.setOnClickListener {
            ToastUtil.showInfo("分享")
        }

    }

    /**
     * 更新喜欢状态
     */
    override fun updateFavoriteState(favorite: Boolean) {
        goodsData.is_like = favorite
        if (favorite) {
            buttonLike.setBackgroundResource(R.drawable.border_round_2d343a)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            buttonLike.setPadding(DimenUtil.dp2px(2.0), 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_already_like)
        } else {
            buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            buttonLike.compoundDrawablePadding = DimenUtil.dp2px(5.0)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_like)
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

    override fun setPresenter(presenter: GoodsDetailContract.Presenter?) {
        setPresenter(presenter)
    }
}