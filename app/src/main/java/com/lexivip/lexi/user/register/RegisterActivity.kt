package com.lexivip.lexi.user.register

import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.R
import com.lexivip.lexi.index.selection.OpenLifeHouseActivity
import com.lexivip.lexi.user.areacode.MessageAreaCode
import com.lexivip.lexi.user.areacode.SelectCountryOrAreaActivity
import com.lexivip.lexi.user.login.LoginActivity
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
    private var type:Int=0
    private var openid:String?=null

    override fun getIntentData() {
        type=intent.getIntExtra("type",0)
        openid=intent.getStringExtra("openid")
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        presenter = RegisterPresenter(this)
        if (type==0) {
            customHeadView.setRightTxt(getString(R.string.text_skip), R.color.color_666)
        }else{
            textViewTitle.setText(AppApplication.getContext().getString(R.string.text_bind))
            button.setText(AppApplication.getContext().getString(R.string.text_bind))
        }
        timeCount = TimeCount(textViewGetCode, 60000, 1000)
    }

    override fun installListener() {
        customHeadView.headRightTV.setOnClickListener(this)

        textViewCountryCode.setOnClickListener(this)

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
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity::class.java.simpleName,TAG)
                startActivity(intent)
                EventBus.getDefault().post(MessageClose())
                finish()
            }
            R.id.button -> {
                if(type==0) {
                    presenter.verifyCheckCode(textViewCountryCode.text.toString(), etPhone.text.toString(), etCheckCode.text.toString())
                }else{
                    presenter.bindPhoneCode(openid!!,textViewCountryCode.text.toString(), etPhone.text.toString(), etCheckCode.text.toString())
                }
            }

            R.id.textViewCountryCode -> startActivity(Intent(this,SelectCountryOrAreaActivity::class.java))

            R.id.textViewGetCode -> {
                presenter.sendCheckCode(textViewCountryCode.text.toString(),etPhone.text.toString())
            }
            R.id.textViewService -> {
                val intent=Intent(this, OpenLifeHouseActivity::class.java)
                intent.putExtra("url","https://h5.lexivip.com/site/service_agreement")
                startActivity(intent)
            }
            R.id.textViewPrivate -> {
                val intent=Intent(this, OpenLifeHouseActivity::class.java)
                intent.putExtra("url","https://h5.lexivip.com/site/privacy")
                startActivity(intent)
            }
            R.id.textViewJump -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun startCountDown() {
        timeCount.start()
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
    fun onMessageEvent(event: MessageAreaCode) {
        textViewCountryCode.text = event.areaCode
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("RegisterActivity be closed")
        finish()
    }

    override fun setBindPhoneCode() {
        startActivity(Intent(this,MainActivity::class.java))
        EventBus.getDefault().post(MessageClose())
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