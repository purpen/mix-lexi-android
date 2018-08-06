package com.thn.lexi.goods.selection

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HotPeopleGridSpaceDecoration(dimen:Int): RecyclerView.ItemDecoration() {
    //像素值
    private var dimen = dimen
    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent?.getChildAdapterPosition(view)
        if (position==1 || position==4){
            outRect.right = 0
        }else{
            outRect.right = dimen
        }
        outRect.bottom = dimen
    }
}