package com.thn.lexi.user.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.ui.BaseActivity
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.thn.lexi.MainActivity
import com.thn.lexi.R
import com.thn.lexi.user.password.ForgetPasswordActivity
import com.thn.lexi.user.register.RegisterActivity
import kotlinx.android.synthetic.main.acticity_login.*

/**
 * 登录
 */
class LoginActivity : BaseActivity(), View.OnClickListener, LoginContract.View {

    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: LoginPresenter

    private var showPassword:Boolean = false

    override val layout: Int = R.layout.acticity_login

    override fun initView() {
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


    override fun setPresenter(presenter: com.thn.lexi.user.login.LoginContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.tv_head_right -> startActivity(Intent(applicationContext, MainActivity::class.java))

            R.id.textViewPasswordLogin ->{
                relativeLayoutCheckCodeBox.visibility = View.GONE
                relativeLayoutPassWordBox.visibility = View.VISIBLE
                textViewPasswordLogin.setTextColor(resources.getColor(R.color.color_6ed7af))
                textViewCheckCodeLogin.setTextColor(resources.getColor(R.color.color_333))
            }

            R.id.textViewCheckCodeLogin ->{
                relativeLayoutPassWordBox.visibility = View.GONE
                relativeLayoutCheckCodeBox.visibility = View.VISIBLE
                textViewCheckCodeLogin.setTextColor(resources.getColor(R.color.color_6ed7af))
                textViewPasswordLogin.setTextColor(resources.getColor(R.color.color_333))
            }

            R.id.textViewCountryCode ->{
                ToastUtil.showInfo("显示国家代码")
            }

            R.id.textViewGetCode->{
                presenter.getCheckCode(etPhone.text.toString())
            }

            R.id.imageViewShow->{
                if(showPassword){
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                }else{
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                }

            }

            R.id.textViewJump -> startActivity(Intent(applicationContext, RegisterActivity::class.java))

            R.id.textViewForgetPassword -> startActivity(Intent(applicationContext, ForgetPasswordActivity::class.java))

            R.id.btnLogin -> presenter.loginUser(etPhone.text.toString(),etPassword.text.toString())

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

    override fun goPage() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showInfo(message: String) {
        ToastUtil.showInfo(message)
    }

}