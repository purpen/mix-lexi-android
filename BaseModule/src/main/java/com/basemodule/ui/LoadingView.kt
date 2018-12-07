package com.basemodule.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.lexivip.basemodule.R

class LoadingView : RelativeLayout {
    interface LoadingViewListener {
        fun onFailClickListener()
    }

    private var mListener: LoadingViewListener? = null

    fun setListener(listener: LoadingViewListener) {
        this.mListener = listener
    }

    private lateinit var mRelativeLayoutLoading: RelativeLayout
    private lateinit var mRelativeLayoutFail: RelativeLayout
    private lateinit var imageViewLoading: ImageView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    private fun initViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_loading, this)
        mRelativeLayoutLoading = view.findViewById(R.id.relativeLayoutLoading)
        mRelativeLayoutFail = view.findViewById(R.id.relativeLayoutFail)
        imageViewLoading = view.findViewById<ImageView>(R.id.imageViewLoading)
        setLoadingImageLayoutParams()
        GlideUtil.loadingImage(R.drawable.loading_image, imageViewLoading)
        mRelativeLayoutFail.setOnClickListener {
            mRelativeLayoutLoading.visibility = View.VISIBLE
            mRelativeLayoutFail.visibility = View.GONE
            mListener?.onFailClickListener()
        }
    }

    /**
     * 界面到屏幕顶部距离
     */
    fun setOffsetTop(offsetDp: Int) {
        setLoadingImageLayoutParams(offsetDp)
    }

    /**
     * 设置ImageView的位置
     */
    private fun setLoadingImageLayoutParams(offsetDp: Int = 0) {
        val dp64 = DimenUtil.dp2px(64.0)
        val layoutParams = RelativeLayout.LayoutParams(dp64, dp64)
        layoutParams.topMargin = (ScreenUtil.getScreenHeight() - dp64) / 2 - offsetDp
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        imageViewLoading.layoutParams = layoutParams
    }

    /**
     * 显示加载过程中的状态
     */
    fun show() {
        mRelativeLayoutLoading.visibility = View.VISIBLE
        mRelativeLayoutFail.visibility = View.GONE
    }

    /**
     * 显示加载完成的状态
     */
    fun dismiss() {
        mRelativeLayoutLoading.visibility = View.GONE
        mRelativeLayoutFail.visibility = View.GONE
    }

    /**
     * 显示加载失败的状态
     */
    fun showError() {
        mRelativeLayoutLoading.visibility = View.GONE
        mRelativeLayoutFail.visibility = View.VISIBLE
    }
}