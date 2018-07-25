package com.thn.lexi.user.password

import android.content.Intent
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import com.thn.lexi.user.areacode.MessageAreaCode
import com.thn.lexi.user.areacode.SelectCountryOrAreaActivity
import kotlinx.android.synthetic.main.acticity_forget_password.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 忘记密码
 */
class ForgetPasswordActivity : BaseActivity(), View.OnClickListener, ForgetPasswordContract.View {

    private val dialog:WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: ForgetPasswordPresenter

    override val layout: Int = R.layout.acticity_forget_password

    override fun initView() {
        EventBus.getDefault().register(this)
        presenter = ForgetPasswordPresenter(this)
    }

    override fun installListener() {
        button.setOnClickListener(this)
        textViewCountryCode.setOnClickListener(this)
        textViewGetCode.setOnClickListener(this)
    }


    override fun setPresenter(presenter: ForgetPasswordContract.Presenter?) {
       setPresenter(presenter)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.textViewCountryCode -> startActivity(Intent(applicationContext, SelectCountryOrAreaActivity::class.java))
            R.id.button -> {
                presenter.verifyCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString(),etCheckCode.text.toString())
                //TODO 验证动态码正确，跳转设置新密码
                startActivity(Intent(this,SetNewPasswordActivity::class.java))
            }
            R.id.textViewGetCode -> presenter.sendCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString())

        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageAreaCode) {
        textViewCountryCode.text = event.areaCode
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

    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }

}