package com.lexivip.lexi.user.register

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.MessageClose
import com.lexivip.lexi.R
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import kotlinx.android.synthetic.main.activity_set_password.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SetPasswordActivity : BaseActivity(), SetPasswordContract.View, View.OnClickListener {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }
    private lateinit var registerBean: RegisterBean

    private lateinit var presenter: SetPasswordPresenter

    private var showPassword: Boolean = false
    private var showPassword1: Boolean = false

    override val layout: Int = R.layout.activity_set_password

    override fun getIntentData() {
        registerBean = intent.extras.get(RegisterActivity::class.java.simpleName) as RegisterBean
    }

    override fun initView() {
        EventBus.getDefault().register(this)
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
                    imageViewShow.setImageResource(R.mipmap.icon_hidden_password)
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    imageViewShow.setImageResource(R.mipmap.icon_show_password)
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
            R.id.imageViewShow1 -> {
                if (showPassword1) {
                    imageViewShow1.setImageResource(R.mipmap.icon_hidden_password)
                    showPassword1 = false
                    etPassword1.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    imageViewShow1.setImageResource(R.mipmap.icon_show_password)
                    showPassword1 = true
                    etPassword1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
        //注册用户
            R.id.button -> presenter.registerUser(registerBean.data.areacode,registerBean.data.email, etPassword.text.toString(),etPassword1.text.toString())
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
        startActivity(Intent(this, CompleteInfoActivity::class.java))
    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("SetPasswordActivity be closed")
        finish()
    }
}