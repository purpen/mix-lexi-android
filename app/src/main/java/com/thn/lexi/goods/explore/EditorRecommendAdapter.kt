package com.thn.lexi.goods.explore

import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class EditorRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {
        val textViewStatus = helper.getView<View>(R.id.textViewStatus)
        val textViewExpress = helper.getView<View>(R.id.textViewExpress)
        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewPrice,item.min_sale_price)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}