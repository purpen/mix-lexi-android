package com.thn.lexi.user.register

import android.content.Intent
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.MainActivity
import com.thn.lexi.R
import com.thn.lexi.user.login.LoginActivity
import kotlinx.android.synthetic.main.acticity_register.*

/**
 * 注册presenter.registerUser(editText0.text.toString(),editText1.text.toString(),editText2.text.toString())
 */
class RegisterActivity : BaseActivity(), View.OnClickListener, RegisterContract.View {

    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: RegisterPresenter

    override val layout: Int = R.layout.acticity_register

    override fun initView() {
        presenter = RegisterPresenter(this)
        customHeadView.setHeadCenterTxtShow(true, getString(R.string.title_register))
    }

    override fun installListener() {
        button.setOnClickListener(this)
        textViewGetCode.setOnClickListener(this)
        textViewService.setOnClickListener(this)
        textViewPrivate.setOnClickListener(this)
        textViewJump.setOnClickListener(this)
    }


    override fun setPresenter(presenter: RegisterContract.Presenter?) {
       setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.button -> presenter.verifyCheckCode(etPhone.text.toString(),etCheckCode.text.toString())
            R.id.textViewGetCode -> presenter.sendCheckCode(etPhone.text.toString())
            R.id.textViewService -> ToastUtil.showInfo("服务条款")
            R.id.textViewPrivate -> ToastUtil.showInfo("隐私条款")
            R.id.textViewJump -> startActivity(Intent(this,LoginActivity::class.java))
        }
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

    override fun goPage() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }

}