package com.lexivip.lexi.search

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterSearchRecentLookGoods(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    private val dp100 = DimenUtil.getDimensionPixelSize(R.dimen.dp100)
    private val dp4 = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.itemView.layoutParams = ViewGroup.LayoutParams(dp100, ViewGroup.LayoutParams.WRAP_CONTENT)
        val imageViewStatus = helper.getView<View>(R.id.imageViewStatus)
        if (item.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        val imageViewExpress = helper.getView<View>(R.id.imageViewExpress)

        if (item.is_free_postage) {
            imageViewExpress.visibility = View.VISIBLE
        } else {
            imageViewExpress.visibility = View.GONE
        }


        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(dp100, dp100)
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView, dp4,dp100)

        helper.setText(R.id.textViewTitle, item.name)

        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)

        if (item.min_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${item.min_price}")
            textViewOldPrice.visibility = View.GONE
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${item.min_sale_price}")
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥" + item.min_price
        }
    }
}