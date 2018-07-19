package com.thn.lexi.user.register

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_set_password.*

class SetPasswordActivity : BaseActivity(), SetPasswordContract.View, View.OnClickListener {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }
    private lateinit var phone: String

    private lateinit var presenter: SetPasswordPresenter

    private var showPassword: Boolean = false
    private var showPassword1: Boolean = false

    override val layout: Int = R.layout.activity_set_password

    override fun getIntentData() {
        phone = intent.extras.get(RegisterActivity::class.java.simpleName) as String
    }

    override fun initView() {
        presenter = SetPasswordPresenter(this)
    }

    override fun setPresenter(presenter: SetPasswordContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        imageViewShow.setOnClickListener(this)
        imageViewShow1.setOnClickListener(this)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageViewShow -> {
                if (showPassword) {
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                } else {
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                }
            }
            R.id.imageViewShow1 -> {
                if (showPassword1) {
                    showPassword1 = false
                    etPassword1.transformationMethod = PasswordTransformationMethod.getInstance();
                } else {
                    showPassword1 = true
                    etPassword1.transformationMethod = HideReturnsTransformationMethod.getInstance();
                }
            }
        //注册用户
            R.id.button -> presenter.registerUser(phone, etPassword.text.toString(), "")
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

    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }
}