package com.lexivip.lexi
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class StaggeredDividerItemDecoration(dividerBottomDp: Int, dividerLeftDp: Int) : RecyclerView.ItemDecoration() {
    private var dividerLeft:Int = dividerLeftDp
    private var dividerBottom:Int = dividerBottomDp

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams


        if (params.spanIndex % 2 == 0){
            outRect.left = 0
        }else{
            outRect.left = dividerLeft
        }

        outRect.bottom = dividerBottom
    }
}