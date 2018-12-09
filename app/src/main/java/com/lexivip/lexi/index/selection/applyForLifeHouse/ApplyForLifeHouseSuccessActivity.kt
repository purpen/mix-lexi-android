package com.lexivip.lexi.index.selection.applyForLifeHouse

import android.content.Intent
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.activity_apply_for_lifehouse_success.*

class ApplyForLifeHouseSuccessActivity : BaseActivity() {
    override val layout: Int = R.layout.activity_apply_for_lifehouse_success
    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_apply_for_lifehouse)
    }

    override fun installListener() {
        customHeadView.setGoBackListener {
            jump2MainPage()
        }

        button.setOnClickListener {
            jump2MainPage()
        }
    }

    private fun jump2MainPage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity::class.java.simpleName, TAG)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        jump2MainPage()
    }
}