package com.lexivip.lexi.welcome

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.cashMoney.CashMoneyActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.greenrobot.eventbus.EventBus
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.index.selection.applyForLifeHouse.OpenLifeHouseActivity
import com.lexivip.lexi.mine.UserCenterBean
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.user.LoginWXBean
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import com.lexivip.lexi.user.setting.SettingContract
import com.lexivip.lexi.user.setting.SettingPresenter
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class WelcomeActivity : BaseActivity(), View.OnClickListener, WelcomeContract.View{
    override val layout: Int = R.layout.activity_welcome
    private var bitmap: Bitmap?=null
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }
    private lateinit var presenter: WelcomePresenter
    private var openid:String?=null
    override fun initView() {
        EventBus.getDefault().register(this)
        presenter = WelcomePresenter(this)
        val options = BitmapFactory.Options()
        bitmap = BitmapFactory.decodeResource(resources,R.mipmap.img_bg_welcome, options)
        imageViewBg.setImageBitmap(ImageUtil.scaleImage(bitmap,ScreenUtil.getScreenWidth(),ScreenUtil.getScreenHeight()))
    }

    override fun installListener() {
        textViewSkip.setOnClickListener(this)
        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        linearLayout.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.textViewSkip -> {
                startActivity(Intent(this, MainActivity::class.java))
                //finish()
            }
            R.id.button0 -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.button1 -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.linearLayout -> {
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN,umAuthListener)
            }
        }
    }

    internal var umAuthListener: UMAuthListener = object : UMAuthListener {
        override fun onStart(share_media: SHARE_MEDIA) {

        }

        override fun onComplete(share_media: SHARE_MEDIA, i: Int, map: Map<String, String>) {
            LogUtil.e("授权回调成功了："+map.get("unionid"))
            openid=map.get("unionid")
            presenter.bindWX(map)
        }

        override fun onError(share_media: SHARE_MEDIA, i: Int, throwable: Throwable) {
            LogUtil.e("授权回调失败："+throwable.message)
            ToastUtil.showError("授权回调失败")
        }

        override fun onCancel(share_media: SHARE_MEDIA, i: Int) {
            LogUtil.e("取消授权")
            ToastUtil.showError("取消授权")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("WelcomeActivity be closed")
        finish()
    }

    override fun onDestroy() {
        bitmap?.recycle()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun setPresenter(presenter: WelcomeContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun showError(s: String) {
        ToastUtil.showError(s)
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun setBind() {
        val intent=Intent(this, RegisterActivity::class.java)
        intent.putExtra("type",1)
        intent.putExtra("openid",openid)
        startActivity(intent)
    }
    override fun setFinish() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}