package com.thn.lexi.index.selection

import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.index.bean.ProductBean

class TodayRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ProductBean) {

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewTitle1,item.store_name)
        helper.setText(R.id.textViewTitle2,item.features)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}