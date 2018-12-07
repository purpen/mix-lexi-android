package com.lexivip.lexi.index.selection.applyForLifeHouse

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.CustomCountDownTimer
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.user.areacode.MessageAreaCode
import com.lexivip.lexi.user.areacode.SelectCountryOrAreaActivity
import com.lexivip.lexi.user.login.ApplyForLifeHouseBean
import com.lexivip.lexi.user.login.UserProfileBean
import kotlinx.android.synthetic.main.activity_apply_for_lifehouse.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ApplyForLifeHouseActivity : BaseActivity(), ApplyForLifeHouseContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.activity_apply_for_lifehouse
    private lateinit var timeCount: CustomCountDownTimer
    private val presenter: ApplyForLifeHousePresenter by lazy { ApplyForLifeHousePresenter(this) }
    override fun initView() {
        EventBus.getDefault().register(this)
        timeCount = CustomCountDownTimer(60000, 1000, textViewGetCode)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_apply_for_lifehouse)
        GlideUtil.loadImageWithDimen(R.mipmap.icon_header_apply_for_lifehouse, imageViewBg, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(227.0), ImageSizeConfig.DEFAULT)
    }

    override fun installListener() {
        textViewCountryCode.setOnClickListener {
            startActivity(Intent(this, SelectCountryOrAreaActivity::class.java))
        }

        textViewGetCode.setOnClickListener {
            val phone = etPhone.text.trim().toString()
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showInfo(getString(R.string.phone_num))
                return@setOnClickListener
            }
            presenter.sendCheckCode(textViewCountryCode.text.toString(), phone)
        }

        button.setOnClickListener {
            val name = etName.text.trim().toString()
            if (TextUtils.isEmpty(name)) {
                textViewHintCheckcode.text = getString(R.string.hint_real_name)
                textViewHintCheckcode.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val job = etJob.text.trim().toString()
            if (TextUtils.isEmpty(job)) {
                textViewHintCheckcode.text = getString(R.string.hint_your_job)
                textViewHintCheckcode.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val phone = etPhone.text.trim().toString()
            if (TextUtils.isEmpty(phone)) {
                textViewHintCheckcode.text = getString(R.string.phone_num)
                textViewHintCheckcode.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val checkCode = etCheckCode.text.trim().toString()
            if (TextUtils.isEmpty(checkCode)) {
                textViewHintCheckcode.text = getString(R.string.text_input_check_code)
                textViewHintCheckcode.visibility = View.VISIBLE
                return@setOnClickListener
            }

            presenter.applyForLifeHouse(name, job, textViewCountryCode.text.toString(), phone, checkCode)
        }
    }

    /**
     * 开启倒计时
     */
    override fun startCountDown() {
        timeCount.start()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageAreaCode) {
        textViewCountryCode.text = event.areaCode
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * 提交成功跳转成功页
     */
    override fun applySuccess(applyForLifeHouseBean: ApplyForLifeHouseBean) {
        EventBus.getDefault().post(MessageClose())
        val userProfile = SPUtil.read(Constants.USER_PROFILE)
        if (!TextUtils.isEmpty(userProfile)) {
            val userProfileBean = JsonUtil.fromJson(userProfile, UserProfileBean::class.java)
            userProfileBean.data.is_small_b = true
            userProfileBean.data.store_rid = applyForLifeHouseBean.data.store_rid
            SPUtil.write(Constants.USER_PROFILE, JsonUtil.toJson(userProfileBean))
        }
        startActivity(Intent(this, ApplyForLifeHouseSuccessActivity::class.java))
        finish()
    }

    override fun showLoadingView() {
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun showInfo(message: String) {
        ToastUtil.showInfo(message)
    }

    override fun setPresenter(presenter: ApplyForLifeHouseContract.Presenter?) {
        setPresenter(presenter)
    }
}