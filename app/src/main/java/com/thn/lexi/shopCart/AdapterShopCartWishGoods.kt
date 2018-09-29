package com.thn.lexi.shopCart

import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean

class AdapterShopCartWishGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ProductBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithFading(item.cover, imageView)
        helper.setText(R.id.textViewName, item.name)
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp8,R.dimen.dp10),null,null,null)
        if (item.real_sale_price == 0.0) {
            textViewPrice.text = "${item.real_price}"
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = "${item.real_sale_price}"
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥${item.real_price}"
        }

        val textViewSoldOut = helper.getView<TextView>(R.id.textViewSoldOut)
        val linearLayoutAddShopCart = helper.getView<View>(R.id.linearLayoutAddShopCart)


        if (item.status==1){ //上架状态
            helper.itemView.isEnabled = true
            if (item.total_stock==0){//库存为0，已售罄
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
            helper.itemView.isEnabled = false
            linearLayoutAddShopCart.visibility = View.GONE
            textViewSoldOut.visibility = View.VISIBLE
            textViewSoldOut.text = Util.getString(R.string.text_remove_sold)
        }
    }
}