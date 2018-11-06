package com.lexivip.lexi

import android.os.CountDownTimer
import android.widget.TextView
import java.lang.ref.WeakReference

class CustomCountDownTimer(millisInFuture:Long, countDownInterval:Long,textView: TextView): CountDownTimer(millisInFuture, countDownInterval) {
    private val weakReference: WeakReference<TextView> = WeakReference(textView)
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