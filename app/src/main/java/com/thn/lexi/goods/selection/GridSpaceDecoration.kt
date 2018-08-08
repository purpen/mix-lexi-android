package com.thn.lexi.goods.selection

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpaceDecoration(dimenRight: Int, dimenBottom: Int) : RecyclerView.ItemDecoration() {
    //像素值
    private var dimenR = dimenRight
    private var dimenB = dimenBottom

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view)
        outRect.bottom = dimenB
        if (position % 2 == 0) {
            outRect.right = dimenR
        } else {
            outRect.right = 0
        }

    }
}