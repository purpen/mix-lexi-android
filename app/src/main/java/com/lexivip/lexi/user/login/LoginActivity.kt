package com.lexivip.lexi.user.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.tools.LogUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.lexivip.lexi.CustomCountDownTimer
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.R
import com.lexivip.lexi.user.areacode.MessageAreaCode
import com.lexivip.lexi.user.areacode.SelectCountryOrAreaActivity
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import com.lexivip.lexi.user.password.ForgetPasswordActivity
import com.lexivip.lexi.user.register.RegisterActivity
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.acticity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 登录
 */
class LoginActivity : BaseActivity(), View.OnClickListener, LoginContract.View {

    private val dialog:WaitingDialog by lazy { WaitingDialog(this) }

    private lateinit var presenter: LoginPresenter

    private lateinit var timeCount: CustomCountDownTimer
    private var showPassword:Boolean = false
    private var openid:String?=null

    override val layout: Int = R.layout.acticity_login

    override fun initView() {
        EventBus.getDefault().register(this)
        this.presenter = LoginPresenter(this)
        customHeadView.setRightTxt(getString(R.string.text_skip),Util.getColor(R.color.color_666))
        timeCount = CustomCountDownTimer(60000,1000,textViewGetCode)
    }

    override fun installListener() {
        customHeadView.headRightTV.setOnClickListener(this)

        textViewCheckCodeLogin.setOnClickListener(this)

        textViewPasswordLogin.setOnClickListener(this)

        textViewCountryCode.setOnClickListener(this)

        textViewGetCode.setOnClickListener(this)

        btnLogin.setOnClickListener(this)

        textViewForgetPassword.setOnClickListener(this)

        textViewJump.setOnClickListener(this)

        linearLayoutWeChat.setOnClickListener(this)

        imageViewShow.setOnClickListener(this)
//        linearLayoutQQ.setOnClickListener(this)
//        linearLayoutSina.setOnClickListener(this)
    }


    override fun setPresenter(presenter: com.lexivip.lexi.user.login.LoginContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.tv_head_right -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                EventBus.getDefault().post(MessageClose())
                finish()
            }

            R.id.textViewPasswordLogin ->{
                relativeLayoutCheckCodeBox.visibility = View.GONE
                relativeLayoutPassWordBox.visibility = View.VISIBLE
                textViewPasswordLogin.setTextColor(Util.getColor(R.color.color_6ed7af))
                textViewCheckCodeLogin.setTextColor(Util.getColor(R.color.color_333))
            }

            R.id.textViewCheckCodeLogin ->{
                relativeLayoutPassWordBox.visibility = View.GONE
                relativeLayoutCheckCodeBox.visibility = View.VISIBLE
                textViewCheckCodeLogin.setTextColor(Util.getColor(R.color.color_6ed7af))
                textViewPasswordLogin.setTextColor(Util.getColor(R.color.color_333))
            }

            R.id.textViewCountryCode ->{
                startActivity(Intent(applicationContext, SelectCountryOrAreaActivity::class.java))
            }

            R.id.textViewGetCode->{
                presenter.sendCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString())
            }

            R.id.imageViewShow->{
                if(showPassword){
                    imageViewShow.setImageResource(R.mipmap.icon_hidden_password)
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                }else{
                    imageViewShow.setImageResource(R.mipmap.icon_show_password)
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                }
            }

            R.id.textViewJump -> startActivity(Intent(applicationContext, RegisterActivity::class.java))

            R.id.textViewForgetPassword -> startActivity(Intent(applicationContext, ForgetPasswordActivity::class.java))

            R.id.btnLogin -> {
                if(relativeLayoutPassWordBox.isShown){
                    presenter.loginUser(etPhone.text.toString(),etPassword.text.toString())
                }else{
                    presenter.loginUserWithCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString(),etCheckCode.text.toString())
                }
            }

            R.id.linearLayoutWeChat -> UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN,umAuthListener)
                //UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener)

//            R.id.linearLayoutQQ -> presenter.qqLogin()
//
//            R.id.linearLayoutSina -> presenter.sinaLogin()
        }
    }

    internal var umAuthListener: UMAuthListener = object : UMAuthListener {
        override fun onStart(share_media: SHARE_MEDIA) {

        }

        override fun onComplete(share_media: SHARE_MEDIA, i: Int, map: Map<String, String>) {
            LogUtil.e("授权回调成功了："+map.get("unionid"))
            openid=map.get("unionid")
            presenter.wechatLogin(map)
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

    override fun setBind() {
        val intent=Intent(this, RegisterActivity::class.java)
        intent.putExtra("type",1)
        intent.putExtra("openid",openid)
        startActivity(intent)
    }

    override fun startCountDown() {
        timeCount.start()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun goPage(vararg args: Boolean) {
        if (args.isEmpty() || !args[0]){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity::class.java.simpleName,TAG)
            startActivity(intent)
            EventBus.getDefault().post(MessageClose())
        }else{
            startActivity(Intent(this, CompleteInfoActivity::class.java))
        }
    }

    override fun showInfo(message: String) {
        ToastUtil.showInfo(message)
    }

    override fun showHint() {
        textViewHint.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageAreaCode) {
        textViewCountryCode.text = event.areaCode
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("LoginActivity be closed")
        finish()
    }
}