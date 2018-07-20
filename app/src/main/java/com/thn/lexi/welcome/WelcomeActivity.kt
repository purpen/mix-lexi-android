package com.thn.lexi.welcome

import android.content.Intent
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseActivity
import com.thn.lexi.MainActivity
import com.thn.lexi.R
import com.thn.lexi.user.login.LoginActivity
import com.thn.lexi.user.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.greenrobot.eventbus.EventBus
import com.basemodule.tools.LogUtil
import com.thn.lexi.MessageClose
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class WelcomeActivity : BaseActivity(), View.OnClickListener {
    override val layout: Int = R.layout.activity_welcome

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun installListener() {
        textViewSkip.setOnClickListener(this)
        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        linearLayout.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.textViewSkip -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
            R.id.button0 -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.button1 -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.linearLayout -> ToastUtil.showInfo("微信登录")
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("WelcomeActivity be closed")
        finish()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy()
    }
}