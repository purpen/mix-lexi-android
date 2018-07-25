package com.thn.lexi.user.completeinfo

import android.content.Intent
import android.view.View
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_complete_info.*
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.thn.lexi.MainActivity
import com.thn.lexi.MessageClose
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*


class CompleteInfoActivity : BaseActivity(), CompleteInfoContract.View, View.OnClickListener {
    private var gender: String = "0" //默认性别女
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }

    override val layout: Int = R.layout.activity_complete_info

    private lateinit var presenter: CompleteInfoPresenter

    override fun initView() {
        presenter = CompleteInfoPresenter(this)
    }

    override fun installListener() {
        relativeLayoutOval.setOnClickListener(this)
        textViewBirth.setOnClickListener(this)
        button.setOnClickListener(this)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton0 -> gender = "0"
                R.id.radioButton1 -> gender = "1"
            }
        }
    }

    override fun setPresenter(presenter: CompleteInfoContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.relativeLayoutOval -> {
//                弹框截图上传
//                presenter.uploadAvatar()
            }

            R.id.textViewBirth -> {
                //默认选今天
                val selectedDate = Calendar.getInstance()
                val startDate = Calendar.getInstance()
                //开始日期
                startDate.set(1900, 0, 1)
                //截止日期
                val endDate = Calendar.getInstance()
                endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH))
                val pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                    textViewBirth.setTextColor(resources.getColor(R.color.color_333))
                    textViewBirth.text = getTime(date)
                }).setRangDate(startDate, endDate)
                        .setDate(selectedDate)
                        .build()
                pvTime.show()
            }

            R.id.button -> {
                //提交补充信息关闭之前界面
                presenter.uploadUserInfo("avatar_id", etName.text.toString(), textViewBirth.text.toString(),gender)
                EventBus.getDefault().post(MessageClose())
                finish()
            }
        }
    }

    override fun goPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun showInfo(string: String) {

    }

    override fun showError(s: String) {
        ToastUtil.showError(s)
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    private fun getTime(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE)
        return format.format(date)
    }
}