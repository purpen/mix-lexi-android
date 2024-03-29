package com.lexivip.lexi.view

import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.basemodule.tools.Util
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.view_custom_bottom_bar.view.*

/**
 * 底部导航栏
 */
class CustomBottomBar : LinearLayout, View.OnClickListener {
    private var onTabClickListener: ((View) -> Unit)? = null

    fun setOnTabClickListener(listener:(View)->Unit){
        this.onTabClickListener = listener
    }
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_bottom_bar, this)
        button0.isSelected = true
        installListener()
    }

    private fun installListener() {
        button0.setOnClickListener(this)

        button1.setOnClickListener(this)

        button2.setOnClickListener(this)

        button3.setOnClickListener(this)

    }

    public fun getButton(@IdRes id:Int):Button{
        return findViewById(id)
    }

    override fun onClick(v: View?) {
        val button: Button? = v as? Button
        val id = button?.id
//        resetEnableState()
//        button?.isSelected = true
//        button?.setTextColor(Util.getColor(R.color.color_6ed7af))
        when (id) {
            R.id.button0 -> onTabClickListener?.invoke(v)
            R.id.button1 -> onTabClickListener?.invoke(v)
            R.id.button2 -> onTabClickListener?.invoke(v)
            R.id.button3 -> onTabClickListener?.invoke(v)
        }
    }

    fun setTabChecked(v: View?){
        resetEnableState()
        val button = v as Button
        button.isSelected = true
        button.setTextColor(Util.getColor(R.color.color_6ed7af))
    }

    private fun resetEnableState() {
        button0.isSelected = false
        button0.setTextColor(Util.getColor(R.color.color_666))

        button1.isSelected = false
        button1.setTextColor(Util.getColor(R.color.color_666))

        button2.isSelected = false
        button2.setTextColor(Util.getColor(R.color.color_666))

        button3.isSelected = false
        button3.setTextColor(Util.getColor(R.color.color_666))
    }
}