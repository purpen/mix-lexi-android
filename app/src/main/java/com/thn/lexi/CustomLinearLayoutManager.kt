package com.thn.lexi

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class CustomLinearLayoutManager(context:Context): LinearLayoutManager(context) {

    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}