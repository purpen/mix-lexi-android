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
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.R
import com.lexivip.lexi.user.areacode.MessageAreaCode
import com.lexivip.lexi.user.areacode.SelectCountryOrAreaActivity
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import com.lexivip.lexi.user.password.ForgetPasswordActivity
import com.lexivip.lexi.user.register.RegisterActivity
import kotlinx.android.synthetic.main.acticity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 登录
 */
class LoginActivity : BaseActivity(), View.OnClickListener, LoginContract.View {

    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: LoginPresenter

    private var showPassword:Boolean = false

    override val layout: Int = R.layout.acticity_login

    override fun initView() {
        EventBus.getDefault().register(this)
        this.presenter = LoginPresenter(this)
        customHeadView.setRightTxt(getString(R.string.text_skip),R.color.color_666)
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

            R.id.linearLayoutWeChat -> presenter.wechatLogin()

//            R.id.linearLayoutQQ -> presenter.qqLogin()
//
//            R.id.linearLayoutSina -> presenter.sinaLogin()
        }
    }


    override fun showLoadingView() {
        dialog?.show()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun goPage(vararg args: Boolean) {
        if (args.isEmpty() || !args[0]){
            startActivity(Intent(this, MainActivity::class.java))
            EventBus.getDefault().post(MessageClose())
        }else{
            startActivity(Intent(this, CompleteInfoActivity::class.java))
        }
    }

    override fun showInfo(message: String) {
        ToastUtil.showInfo(message)
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this);
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