package com.thn.lexi

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v7.widget.RecyclerView
import com.basemodule.tools.Util
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class DividerItemDecoration(context: Context, @ColorRes color: Int, recyclerView: RecyclerView) : Y_DividerItemDecoration(context) {
    private var height: Float = 11f
    private var leftPadding: Float = 0f
    private var rightPadding: Float = 0f

    constructor(context: Context, @ColorRes color: Int, recyclerView: RecyclerView, dividerHeight: Float) : this(context, color, recyclerView) {
        height = dividerHeight
    }

    constructor(context: Context, @ColorRes color: Int, recyclerView: RecyclerView, dividerHeight: Float, startPadding: Float, endPadding: Float) : this(context, color, recyclerView) {
        height = dividerHeight
        leftPadding = startPadding
        rightPadding = endPadding
    }

    private val color: Int = Util.getColor(color)
    private val adapter = recyclerView.adapter
    override fun getDivider(itemPosition: Int): Y_Divider? {
        val count = adapter.itemCount
        var divider: Y_Divider? = null
        when (itemPosition) {
            count - 2 -> {

                divider = Y_DividerBuilder()
                        .setBottomSideLine(false, color, 0f, 0f, 0f)
                        .create()
            }

            count - 1 -> {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(false, color, 0f, 0f, 0f)
                        .create()
            }

            else -> {
                divider = Y_DividerBuilder()
                        .setBottomSideLine(true, color, height, 0f, 0f)
                        .create()
            }
        }

        return divider
    }

}