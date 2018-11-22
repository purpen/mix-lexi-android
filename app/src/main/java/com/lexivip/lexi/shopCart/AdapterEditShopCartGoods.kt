package com.lexivip.lexi.shopCart

import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.eventBusMessge.MessageUpdate
import com.lexivip.lexi.R
import com.lexivip.lexi.view.AddSubView
import org.greenrobot.eventbus.EventBus

class AdapterEditShopCartGoods(@LayoutRes res: Int) : BaseQuickAdapter<ShopCartBean.DataBean.ItemsBean, BaseViewHolder>(res) {
    private val dp70: Int by lazy { DimenUtil.dp2px(70.0) }
    override fun convert(helper: BaseViewHolder, item: ShopCartBean.DataBean.ItemsBean) {
        val product = item.product
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(product.cover, imageView,0,dp70,ImageSizeConfig.SIZE_P30X2)
        helper.setText(R.id.textViewName, product.product_name)
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp8, R.dimen.dp10), null, null, null)
        if (product.sale_price == 0.0) {
            textViewPrice.text = "${product.price}"
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = "${product.sale_price}"
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥${product.price}"
        }

        if (!TextUtils.isEmpty(product.s_color) && !TextUtils.isEmpty(product.s_model)){
            helper.setText(R.id.textViewSpec, product.s_color + "/" + product.s_model)
        }else if (TextUtils.isEmpty(product.s_color) && !TextUtils.isEmpty(product.s_model)) {
            helper.setText(R.id.textViewSpec, product.s_model)
        } else if (!TextUtils.isEmpty(product.s_color) && TextUtils.isEmpty(product.s_model)) {
            helper.setText(R.id.textViewSpec, product.s_color)
        }

        val textViewShopName = helper.getView<TextView>(R.id.textViewShopName)
        textViewShopName.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_shop_gray, R.dimen.dp10, R.dimen.dp9), null, null, null)
        textViewShopName.text = product.store_name
        val addSubView = helper.getView<AddSubView>(R.id.addSubView)
        addSubView.setValue("${item.quantity}")
        addSubView.setStorageNum(product.stock_quantity)

        addSubView.setOnValueChangedListener { value ->
            item.quantity = value
            EventBus.getDefault().post(MessageUpdate())
        }


        val checkBox = helper.getView<CheckBox>(R.id.checkBox)

        checkBox.isChecked = item.isChecked

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }

        if (item.isEdit) {//编辑状态
            checkBox.visibility = View.VISIBLE
            addSubView.visibility = View.GONE
        } else {
            checkBox.visibility = View.GONE
            addSubView.visibility = View.VISIBLE
        }

        val textViewSoldOut = helper.getView<TextView>(R.id.textViewSoldOut)
        val textViewReselectSpec = helper.getView<TextView>(R.id.textViewReselectSpec)

        if (item.isEdit) { //编辑状态

            textViewReselectSpec.visibility = View.GONE
            addSubView.visibility = View.GONE

            if (product.status == 1) { //上架状态
                if (product.product_total_stock == 0) { //该商品总库存为0，商品售罄
                    textViewSoldOut.visibility = View.VISIBLE
                    textViewSoldOut.text = Util.getString(R.string.text_sold_out)
                    return
                }

                if (product.stock_quantity == 0) {//该SKU库存为0，该规格已售罄
                    textViewSoldOut.visibility = View.VISIBLE
                    textViewSoldOut.text = Util.getString(R.string.text_sku_sold_out)
                } else {
                    textViewSoldOut.visibility = View.GONE
                }

            } else { //下架状态
                textViewSoldOut.visibility = View.VISIBLE
                textViewSoldOut.text = Util.getString(R.string.text_remove_sold)
            }
        } else { //正常状态
            if (product.status == 1) { //上架状态

                if (product.product_total_stock == 0) { //该商品总库存为0，商品售罄
                    textViewReselectSpec.visibility = View.GONE
                    addSubView.visibility = View.GONE
                    textViewSoldOut.visibility = View.VISIBLE
                    textViewSoldOut.text = Util.getString(R.string.text_sold_out)
                    return
                }

                if (product.stock_quantity == 0) {//该SKU库存为0，该规格商品已售罄
                    addSubView.visibility = View.GONE
                    textViewReselectSpec.visibility = View.VISIBLE
                    helper.addOnClickListener(R.id.textViewReselectSpec)
                } else {
                    addSubView.visibility = View.VISIBLE
                    textViewReselectSpec.visibility = View.GONE
                }
                textViewSoldOut.visibility = View.GONE
            } else { //下架状态
                addSubView.visibility = View.GONE
                textViewReselectSpec.visibility = View.GONE
                textViewSoldOut.visibility = View.VISIBLE
                textViewSoldOut.text = Util.getString(R.string.text_remove_sold)
            }
        }


    }
}