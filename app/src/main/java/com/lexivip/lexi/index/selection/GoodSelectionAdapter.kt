package com.lexivip.lexi.index.selection

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class GoodSelectionAdapter(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    private val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth()-DimenUtil.dp2px(40.0))/2 }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {

        val relativeLayout = helper.getView<View>(R.id.relativeLayout)
        var layoutParams = ViewGroup.LayoutParams(sizeSpan2,ViewGroup.LayoutParams.WRAP_CONTENT)
        relativeLayout.layoutParams = layoutParams

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
        textViewLike.visibility = View.GONE
        textViewLike.text = "喜欢 +${item.like_count}"

        helper.setText(R.id.textViewTitle,item.name)

        val textViewOldPrice =helper.getView<TextView>(R.id.textViewOldPrice)

        if (item.min_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${item.min_price}")
            textViewOldPrice.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${item.min_sale_price}")
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥" + item.min_price
        }

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(sizeSpan2,sizeSpan2)
        GlideUtil.loadImageWithRadius(item.cover,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }
}