package com.lexivip.lexi.welcome

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import com.basemodule.tools.ImageUtil
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.MainActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.greenrobot.eventbus.EventBus
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ScreenUtil
import com.lexivip.lexi.eventBusMessge.MessageClose
import com.lexivip.lexi.user.completeinfo.CompleteInfoActivity
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class WelcomeActivity : BaseActivity(), View.OnClickListener {
    override val layout: Int = R.layout.activity_welcome
    private var bitmap:Bitmap?=null
    override fun initView() {
        EventBus.getDefault().register(this)
        val options = BitmapFactory.Options()
        bitmap = BitmapFactory.decodeResource(resources,R.mipmap.img_bg_welcome, options)
        imageViewBg.setImageBitmap(ImageUtil.scaleImage(bitmap,ScreenUtil.getScreenWidth(),ScreenUtil.getScreenHeight()))
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
            R.id.linearLayout -> startActivity(Intent(this, CompleteInfoActivity::class.java))
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageClose) {
        LogUtil.e("WelcomeActivity be closed")
        finish()
    }

    override fun onDestroy() {
        bitmap?.recycle()
        EventBus.getDefault().unregister(this);
        super.onDestroy()
    }
}