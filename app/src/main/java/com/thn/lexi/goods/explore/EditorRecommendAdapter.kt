package com.thn.lexi.goods.explore

import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class EditorRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {
        val imageViewStatus = helper.getView<View>(R.id.imageViewStatus)
        if (item.is_sold_out){
            imageViewStatus.visibility = View.VISIBLE
        }else{
            imageViewStatus.visibility = View.GONE
        }

        val imageViewExpress = helper.getView<View>(R.id.imageViewExpress)

        if (item.is_free_postage){
            imageViewExpress.visibility = View.VISIBLE
        }else{
            imageViewExpress.visibility = View.GONE
        }

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewPrice,item.min_sale_price)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}