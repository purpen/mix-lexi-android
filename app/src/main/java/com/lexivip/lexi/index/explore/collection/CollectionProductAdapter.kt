package com.lexivip.lexi.index.explore.collection

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class CollectionProductAdapter(@LayoutRes res: Int): BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {
    private val size:Int = DimenUtil.dp2px(75.0)
    private val dp4:Int = DimenUtil.dp2px(4.0)
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = ViewGroup.LayoutParams(size,size)
        GlideUtil.loadImageWithDimenAndRadius(item.cover,imageView,dp4,size)
    }
}