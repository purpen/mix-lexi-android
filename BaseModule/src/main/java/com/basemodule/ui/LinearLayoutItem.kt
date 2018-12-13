package com.basemodule.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lexivip.basemodule.R

class LinearLayoutItem : LinearLayout {

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    private fun initViews() {
        val view = View.inflate(context, R.layout.view_notice_item, this)
        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)
    }

    fun getImageView(): ImageView {
        return imageView
    }

    fun getTextView(): TextView {
        return textView
    }
}