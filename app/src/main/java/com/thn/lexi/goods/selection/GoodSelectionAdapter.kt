package com.thn.lexi.goods.selection

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class GoodSelectionAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {

        val pixelSize = helper.itemView.resources.getDimensionPixelSize(R.dimen.dp160)
        var layoutParams = RelativeLayout.LayoutParams(pixelSize,RelativeLayout.LayoutParams.WRAP_CONTENT)
        helper.itemView.layoutParams = layoutParams

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

        val textViewLike = helper.getView<TextView>(R.id.textViewLike)
        textViewLike.visibility = View.VISIBLE


        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewPrice,item.min_sale_price)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(pixelSize,pixelSize)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}