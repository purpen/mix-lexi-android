package com.lexivip.lexi

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class CustomLinearLayoutManager(context:Context): LinearLayoutManager(context) {

    private var isScrollEnabled = true
    private var canScrollHorizontal = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    /**
     * 水平滚动
     */
    fun setHorizontalScrollEnabled(flag: Boolean) {
        this.canScrollHorizontal = flag
    }
    override fun canScrollHorizontally(): Boolean {
        return canScrollHorizontal && super.canScrollHorizontally()
    }
}