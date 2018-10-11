package com.thn.lexi.user.setting

import android.content.Intent
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.MainActivity
import com.thn.lexi.MainFragment0
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), SettingContract.View, View.OnClickListener {
    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }
    private lateinit var presenter: SettingPresenter
    override val layout: Int = R.layout.activity_setting

    override fun initView() {
        presenter = SettingPresenter(this)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_setting)
        customItemLayout0.setTVStyle(R.mipmap.icon_invite_freinds, R.string.text_invite_freinds, R.color.color_333)
        customItemLayout1.setTVStyle(R.mipmap.icon_find_freind, R.string.text_find_freinds, R.color.color_333)
        customItemLayout2.setTVStyle(R.mipmap.icon_my_orders, R.string.text_my_orders, R.color.color_333)
        customItemLayout3.setTVStyle(R.mipmap.icon_order_address, R.string.text_order_address, R.color.color_333)
        customItemLayout4.setTVStyle(R.mipmap.icon_serve_contract, R.string.text_serve_contract, R.color.color_333)
        customItemLayout5.setTVStyle(R.mipmap.icon_private_items, R.string.text_private_items, R.color.color_333)

    }

    override fun setPresenter(presenter: SettingContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun requestNet() {
        //用户需先登录
        presenter.loadData("")
    }

    override fun installListener() {
        relativeLayout.setOnClickListener(this)
        customItemLayout0.setOnClickListener(this)
        customItemLayout1.setOnClickListener(this)
        customItemLayout2.setOnClickListener(this)
        customItemLayout3.setOnClickListener(this)
        customItemLayout4.setOnClickListener(this)
        customItemLayout5.setOnClickListener(this)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.relativeLayout -> ToastUtil.showInfo("编辑个人资料")
            R.id.customItemLayout0 -> ToastUtil.showInfo("邀请朋友")
            R.id.customItemLayout1 -> ToastUtil.showInfo("找朋友")
            R.id.customItemLayout2 -> ToastUtil.showInfo("我的订单")
            R.id.customItemLayout3 -> ToastUtil.showInfo("收货地址")
            R.id.customItemLayout4 -> ToastUtil.showInfo("服务条款")
            R.id.customItemLayout5 -> ToastUtil.showInfo("隐私条款")
            R.id.button -> {
                SPUtil.clear(Constants.USER_PROFILE)
                SPUtil.clear(Constants.AUTHORIZATION)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity::class.java.simpleName,MainFragment0::class.java.simpleName)
                startActivity(intent)
                finish()
            }

        }
    }

    override fun setUserInfo(data: UserInfoBean.DataBean) {
        textView0.text = data.username
        GlideUtil.loadCircleImageWidthDimen("https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg",imageView,resources.getDimensionPixelSize(R.dimen.dp60))
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(s: String) {
        ToastUtil.showError(s)
    }


}