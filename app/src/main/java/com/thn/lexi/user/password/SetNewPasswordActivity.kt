package com.thn.lexi.user.password

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.MainActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.acticity_set_new_password.*

/**
 *
 * 忘记密码
 */
class SetNewPasswordActivity : BaseActivity(), View.OnClickListener, SetNewPasswordContract.View {

    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }

    private var showPassword: Boolean = false

    private lateinit var presenter: SetNewPasswordPresenter

    override val layout: Int = R.layout.acticity_set_new_password

    override fun initView() {
        presenter = SetNewPasswordPresenter(this)
    }

    override fun installListener() {
        button.setOnClickListener(this)
        imageViewShow.setOnClickListener(this)
    }


    override fun setPresenter(presenter: SetNewPasswordContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {

            //更新密码
            R.id.button -> presenter.updateNewPassword(etPassword.text.toString())

            //显示密码
            R.id.imageViewShow -> {
                if (showPassword) {
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                } else {
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                }

            }


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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }

}