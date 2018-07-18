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

class WelcomeActivity : BaseActivity(), View.OnClickListener {
    override val layout: Int = R.layout.activity_welcome

    override fun initView() {

    }

    override fun installListener() {
        textViewSkip.setOnClickListener(this)
        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        linearLayout.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.textViewSkip -> startActivity(Intent(this, MainActivity::class.java))
            R.id.button0 -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.button1 -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.linearLayout -> ToastUtil.showInfo("微信登录")
        }
    }
}