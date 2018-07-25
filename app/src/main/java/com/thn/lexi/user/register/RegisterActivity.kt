package com.thn.lexi.user.register

import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.MainActivity
import com.thn.lexi.MessageClose
import com.thn.lexi.R
import com.thn.lexi.user.login.LoginActivity
import kotlinx.android.synthetic.main.acticity_register.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * 注册
 */
class RegisterActivity : BaseActivity(), View.OnClickListener, RegisterContract.View {

    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }

    private lateinit var presenter: RegisterPresenter
    override val layout: Int = R.layout.acticity_register
    private lateinit var timeCount: TimeCount

    override fun initView() {
        EventBus.getDefault().register(this)
        presenter = RegisterPresenter(this)
        customHeadView.setRightTxt(getString(R.string.text_skip), R.color.color_666)
        timeCount = TimeCount(textViewGetCode, 60000, 1000)
    }

    override fun installListener() {
        customHeadView.headRightTV.setOnClickListener(this)
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
            R.id.tv_head_right -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                EventBus.getDefault().post(MessageClose())
                finish()
            }
            R.id.button -> {
                presenter.verifyCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString(), etCheckCode.text.toString())

            }
            R.id.textViewGetCode -> {
                timeCount.start()
                presenter.sendCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString())
            }
            R.id.textViewService -> ToastUtil.showInfo("服务条款")
            R.id.textViewPrivate -> ToastUtil.showInfo("隐私条款")
            R.id.textViewJump -> startActivity(Intent(this, LoginActivity::class.java))
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

    override fun goPage(registerBean: RegisterBean) {
        val intent = Intent(this, SetPasswordActivity::class.java)
        intent.putExtra(RegisterActivity::class.java.simpleName, registerBean)
        startActivity(intent)
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
        LogUtil.e("RegisterActivity be closed")
        finish()
    }


    class TimeCount(view: TextView, millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        private val weakReference: WeakReference<TextView> = WeakReference(view)
        private val textView: TextView? = weakReference.get()
        override fun onTick(millisUntilFinished: Long) {
            textView?.isClickable = false
            textView?.text = (millisUntilFinished / 1000).toString() + "s"
        }

        override fun onFinish() {
            textView?.text = "重新获取"
            textView?.isClickable = true
        }
    }
}