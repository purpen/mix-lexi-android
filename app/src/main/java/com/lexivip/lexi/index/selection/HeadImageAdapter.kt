package com.lexivip.lexi.index.selection

import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class HeadImageAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.circleImageView)
        GlideUtil.loadCircleImageWidthDimen(item,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp25))
    }
}