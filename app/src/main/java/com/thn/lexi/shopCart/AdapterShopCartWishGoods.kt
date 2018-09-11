package com.thn.lexi.shopCart

import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.MessageUpdate
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.view.AddSubView
import org.greenrobot.eventbus.EventBus

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
        val linearLayout = helper.getView<View>(R.id.linearLayout)
        if (item.is_sold_out){
            linearLayout.visibility = View.GONE
            textViewSoldOut.visibility = View.VISIBLE
            textViewSoldOut.text = Util.getString(R.string.text_sold_out)
        }else{
            textViewSoldOut.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
        }
    }
}