package com.thn.lexi.user.login

import android.content.Intent
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
class LoginActivity : BaseActivity(), View.OnClickListener, com.thn.lexi.user.login.LoginContract.View {

    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: LoginPresenter

    override val layout: Int = R.layout.acticity_login

    override fun initView() {
        this.presenter = LoginPresenter(this)

        customHeadView.setHeadCenterTxtShow(true, getString(R.string.title_login))

        customHeadView.setRightTxt(getString(R.string.text_phone_login))
    }

    override fun installListener() {
        customHeadView.headRightTV.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        textViewForgetPassword.setOnClickListener(this)
        linearLayoutWeChat.setOnClickListener(this)
        linearLayoutQQ.setOnClickListener(this)
        linearLayoutSina.setOnClickListener(this)
    }


    override fun setPresenter(presenter: com.thn.lexi.user.login.LoginContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.tv_head_right -> startActivity(Intent(applicationContext, RegisterActivity::class.java))

            R.id.textViewForgetPassword -> startActivity(Intent(applicationContext, ForgetPasswordActivity::class.java))

            R.id.btnLogin -> presenter.loginUser(etPhone.text.toString(),etPassword.text.toString())

            R.id.linearLayoutWeChat -> presenter.wechatLogin()

            R.id.linearLayoutQQ -> presenter.qqLogin()

            R.id.linearLayoutSina -> presenter.sinaLogin()
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