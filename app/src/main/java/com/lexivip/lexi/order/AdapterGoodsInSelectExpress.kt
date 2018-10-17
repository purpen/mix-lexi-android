package com.lexivip.lexi.order

import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterGoodsInSelectExpress(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImage(item.cover,imageView)
        helper.setText(R.id.textViewName,item.product_name)
    }
}