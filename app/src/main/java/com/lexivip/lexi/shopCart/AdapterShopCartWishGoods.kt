package com.lexivip.lexi.shopCart

import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.CustomImageSpan
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterShopCartWishGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {
    private val dp70: Int by lazy { DimenUtil.getDimensionPixelSize(R.dimen.dp70) }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)

        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView,0,dp70,ImageSizeConfig.SIZE_P30X2)
        val textViewTitle = helper.getView<TextView>(R.id.textViewName)
        if (item.is_free_postage){
            val drawable = Util.getDrawableWidthPxDimen(R.mipmap.icon_free_express, DimenUtil.dp2px(20.0), DimenUtil.dp2px(12.0))
            val span = CustomImageSpan(drawable)
            val spannable = SpannableString("   " + item.name)
            spannable.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            textViewTitle.text = spannable
        }else{
            textViewTitle.text = item.name
        }
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp8,R.dimen.dp10),null,null,null)
        if (item.min_sale_price == 0.0) {
            textViewPrice.text = "${item.min_price}"
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = "${item.min_sale_price}"
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "¥${item.min_price}"
        }

        val textViewSoldOut = helper.getView<TextView>(R.id.textViewSoldOut)
        val linearLayoutAddShopCart = helper.getView<View>(R.id.linearLayoutAddShopCart)


        if (item.status==1){ //上架状态
            if (item.is_sold_out){//库存为0，已售罄
                linearLayoutAddShopCart.visibility = View.GONE
                textViewSoldOut.visibility = View.VISIBLE
                textViewSoldOut.text = Util.getString(R.string.text_sold_out)
            }else{
                textViewSoldOut.visibility = View.GONE
                linearLayoutAddShopCart.visibility = View.VISIBLE
                //设置加入购物车可点击
                helper.addOnClickListener(R.id.linearLayoutAddShopCart)
            }

        }else{ //下架状态
            linearLayoutAddShopCart.visibility = View.GONE
            textViewSoldOut.visibility = View.VISIBLE
            textViewSoldOut.text = Util.getString(R.string.text_remove_sold)
        }
    }
}