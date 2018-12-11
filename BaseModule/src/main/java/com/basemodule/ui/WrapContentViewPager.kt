package com.basemodule.ui

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.LogUtil

class WrapContentViewPager : ViewPager {

    private var current: Int = 0
    private var viewHeight = 0
    private var isPagingEnabled = true
    private val mChildrenViews: LinkedHashMap<Int, View?> by lazy { LinkedHashMap<Int, View?>() }

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {

    }

    /**
     * ViewPager width = match_parent,height = wrap_content 时 widthMode为EXACTLY屏幕限制，height为AT_MOST(最大为父view高度)
     *ViewPager width = match_parent,height = match_parent 时 widthMode为EXACTLY，height为AT_MOST
     *ViewPager width = match_parent,height = 500 时 widthMode为EXACTLY，height为EXACTLY
     *ViewPager width = wrap_content,height = wrap_content 时 widthMode为AT_MOST，height为AT_MOST
     *       val widthMode = MeasureSpec.getMode(widthMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)
    val heightSize = MeasureSpec.getSize(heightMeasureSpec)
    val widthSize = MeasureSpec.getSize(widthMeasureSpec)

    when (widthMode) {
    MeasureSpec.UNSPECIFIED -> LogUtil.e("UNSPECIFIED===$widthMode；；；heightSize=$widthSize")
    MeasureSpec.AT_MOST -> LogUtil.e("AT_MOST===$widthMode；；；heightSize=$widthSize")
    MeasureSpec.EXACTLY -> LogUtil.e("EXACTLY===$widthMode；；；heightSize=$widthSize")
    }

    when (heightMode) {
    MeasureSpec.UNSPECIFIED -> LogUtil.e("UNSPECIFIED===$heightMode；；；heightSize=$heightSize")
    MeasureSpec.AT_MOST -> LogUtil.e("AT_MOST===$heightMode；；；heightSize=$heightSize")
    MeasureSpec.EXACTLY -> LogUtil.e("EXACTLY===$heightMode；；；heightSize=$heightSize")
    }
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mChildrenViews.size > current) {
            val child = mChildrenViews[current]
            child!!.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            viewHeight = child.measuredHeight
        }
        LogUtil.e("viewHeight=====$viewHeight")
        val heightMS = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMS)
    }

    /**
     * 重置Viewpager高度
     */
    fun resetHeight(position: Int) {
        this.current = position
        if (mChildrenViews.size > current) {
            if (layoutParams == null) {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, viewHeight)
            } else {
                LogUtil.e("设置高度====$viewHeight")
                layoutParams.height = viewHeight
            }
            this.layoutParams = layoutParams
        }
    }

    /**
     * 保存position与对于的View
     */
    fun setObjectForPosition(position: Int, view: View?) {
        LogUtil.e("setObjectForPosition($position: Int, view: View?)")
        mChildrenViews[position] = view
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        try {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event)
        } catch (e: IllegalArgumentException) {
            //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false
        }
    }

    fun setPagingEnabled(b: Boolean) {
        this.isPagingEnabled = b
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }
}