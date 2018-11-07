package com.lexivip.lexi.user.setting
import android.content.Intent
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.MainFragment0
import com.lexivip.lexi.R
import com.lexivip.lexi.mine.UserCenterBean
import kotlinx.android.synthetic.main.activity_setting.*
import android.net.Uri
import com.lexivip.lexi.eventBusMessge.MessageLogout
import com.lexivip.lexi.orderList.OrderListActivity
import com.lexivip.lexi.user.setting.address.AddressListActivity
import com.lexivip.lexi.user.setting.userData.EditUserDataActivity
import org.greenrobot.eventbus.EventBus


class SettingActivity : BaseActivity(), SettingContract.View, View.OnClickListener {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }
    private lateinit var presenter: SettingPresenter
    override val layout: Int = R.layout.activity_setting

    override fun initView() {
        presenter = SettingPresenter(this)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_setting)
        customItemLayout0.setTVStyle(R.mipmap.icon_wx_bind, R.string.text_bind_wx, R.color.color_333)
        customItemLayout0.setTvArrowLeftStrle(true,"未绑定",R.color.color_ff6666,12)
//        customItemLayout1.setTVStyle(R.mipmap.icon_find_freind, R.string.text_find_freinds, R.color.color_333)
        customItemLayout2.setTVStyle(R.mipmap.icon_my_orders, R.string.text_my_orders, R.color.color_333)
        customItemLayout3.setTVStyle(R.mipmap.icon_order_address, R.string.text_order_address, R.color.color_333)

        customItemLayout4.setTVStyle(R.mipmap.icon_apply_shop, R.string.text_open_life_house, R.color.color_333)
        customItemLayout5.setTVStyle(R.mipmap.icon_about_lexi, R.string.text_about_us, R.color.color_333)
        customItemLayout6.setTVStyle(R.mipmap.icon_service_phone,R.string.text_kf_phone,R.color.color_333)

    }

    override fun setPresenter(presenter: SettingContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun requestNet() {
        //用户需先登录
        presenter.loadData()
    }

    override fun installListener() {
        relativeLayout.setOnClickListener(this)
        customItemLayout0.setOnClickListener(this)
        //customItemLayout1.setOnClickListener(this)
        customItemLayout2.setOnClickListener(this)
        customItemLayout3.setOnClickListener(this)
        customItemLayout4.setOnClickListener(this)
        customItemLayout5.setOnClickListener(this)
        customItemLayout6.setOnClickListener(this)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.relativeLayout -> {
                var intent=Intent(this, EditUserDataActivity::class.java)
                startActivityForResult(intent,1)
            }
            R.id.customItemLayout0 -> ToastUtil.showInfo("邀请朋友")
            //R.id.customItemLayout1 -> ToastUtil.showInfo("找朋友")
            R.id.customItemLayout2 -> startActivity(Intent(this, OrderListActivity::class.java))
            R.id.customItemLayout3 -> startActivity(Intent(this,AddressListActivity::class.java))
            R.id.customItemLayout4 -> ToastUtil.showInfo("申请开馆")
            R.id.customItemLayout5 -> ToastUtil.showInfo("隐私条款")
            R.id.customItemLayout6 -> { //拨打客服电话
                val phoneNumber = "400-2345-0000"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)

            }
            R.id.button -> {  //退出登录
                SPUtil.clear(Constants.USER_PROFILE)
                SPUtil.clear(Constants.AUTHORIZATION)
                EventBus.getDefault().post(MessageLogout())
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity::class.java.simpleName, TAG)
                startActivity(intent)
                finish()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if(data!!.getBooleanExtra("isRefresh",false)){
                    presenter.loadData()
                }

            }
        }
    }

    override fun setUserInfo(data: UserCenterBean.DataBean) {
        textView0.text = data.username
        GlideUtil.loadCircleImageWidthDimen(data.avatar, imageView, resources.getDimensionPixelSize(R.dimen.dp60))
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