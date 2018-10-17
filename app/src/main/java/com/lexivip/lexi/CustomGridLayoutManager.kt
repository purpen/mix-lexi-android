package com.lexivip.lexi

import android.content.Context
import android.support.v7.widget.GridLayoutManager

class CustomGridLayoutManager(context:Context,span:Int): GridLayoutManager(context,span) {

    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}