package com.thn.lexi.shopCart

import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.support.annotation.VisibleForTesting
import android.view.View
import android.widget.*
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.MessageUpdate
import com.thn.lexi.R
import com.thn.lexi.view.AddSubView
import org.greenrobot.eventbus.EventBus

class AdapterShopCartGoods(@LayoutRes res: Int) : BaseQuickAdapter<ShopCartBean.DataBean.ItemsBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ShopCartBean.DataBean.ItemsBean) {
        val product = item.product
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithFading(product.cover, imageView)
        helper.setText(R.id.textViewName, product.product_name)
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp8,R.dimen.dp10),null,null,null)
        if (product.real_sale_price == 0.0) {
            textViewPrice.text = "${product.real_price}"
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = "${product.real_sale_price}"
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥${product.real_price}"
        }

        helper.setText(R.id.textViewSpec,product.s_color+" / "+product.s_model)
        val textViewShopName = helper.getView<TextView>(R.id.textViewShopName)
        textViewShopName.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_shop_gray,R.dimen.dp10,R.dimen.dp9),null,null,null)
        textViewShopName.text = product.store_name
        val addSubView = helper.getView<AddSubView>(R.id.addSubView)
        addSubView.setValue("${item.quantity}")
        addSubView.setStorageNum(product.stock_quantity)

        addSubView.setOnValueChangedListener { value ->
            item.quantity = value
            EventBus.getDefault().post(MessageUpdate())
        }

        val checkBox = helper.getView<CheckBox>(R.id.checkBox)

        if (item.isEdit){
            checkBox.visibility = View.VISIBLE
            addSubView.visibility = View.GONE
        }else{
            checkBox.visibility = View.GONE
            addSubView.visibility = View.VISIBLE
        }
    }
}