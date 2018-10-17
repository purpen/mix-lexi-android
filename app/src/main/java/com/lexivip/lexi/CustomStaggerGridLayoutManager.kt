package com.lexivip.lexi

import android.support.v7.widget.StaggeredGridLayoutManager

class CustomStaggerGridLayoutManager(span:Int,orientation:Int): StaggeredGridLayoutManager(span,orientation) {

    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}