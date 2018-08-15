package com.thn.lexi.index.detail

import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class SimilarGoodsAdapter(@LayoutRes res: Int): BaseQuickAdapter<SimilarGoodsBean.DataBean.ProductsBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: SimilarGoodsBean.DataBean.ProductsBean) {
        helper.setText(R.id.textView,item.name)
        helper.setText(R.id.textView1,""+item.max_sale_price)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithTopRadius(item.cover,imageView,mContext.resources.getDimensionPixelSize(R.dimen.dp4))
    }
}