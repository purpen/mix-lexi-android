package com.lexivip.lexi.index.detail

import android.Manifest
import android.content.Intent
import android.support.v4.view.ViewPager
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.activity_goods_image_view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class GoodsImageViewActivity : BaseActivity(), GoodsDetailContract.View , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    override val layout: Int = R.layout.activity_goods_image_view

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: GoodsDetailPresenter by lazy { GoodsDetailPresenter(this) }

    private lateinit var goodsData: GoodsAllDetailBean.DataBean

    override fun getIntentData() {
        goodsData = intent.getParcelableExtra(TAG)
    }

    override fun initView() {
        viewPager.adapter = GoodsImagePagerAdapter(goodsData)
        viewPager.setCurrentItem(goodsData.clickPosition, true)
        textViewNum.text = ("${goodsData.clickPosition + 1} / ${goodsData.assets.size}")

        //设置初始喜欢状态
        if (goodsData.is_like) {
            buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            buttonLike.setPadding(DimenUtil.dp2px(2.0), 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_already_like)
        } else {
            buttonLike.setBackgroundResource(R.drawable.border_round_2d343a)
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
            share()
        }

    }

    /**
     * 更新喜欢状态
     */
    override fun updateFavoriteState(favorite: Boolean) {
        goodsData.is_like = favorite
        if (favorite) {
            buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            buttonLike.setPadding(DimenUtil.dp2px(2.0), 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_already_like)
        } else {
            buttonLike.setBackgroundResource(R.drawable.border_round_2d343a)
            buttonLike.compoundDrawablePadding = DimenUtil.dp2px(5.0)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            buttonLike.text = Util.getString(R.string.text_like)
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            val shareUtil = ShareUtil(this)
            shareUtil.shareGoods(WebUrl.GOODS, WebUrl.AUTH_GOODS, goodsData.assets[0].view_url,
                    goodsData.name,"",goodsData.rid, goodsData.rid+"-"+ goodsData.store_rid,4)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }
}