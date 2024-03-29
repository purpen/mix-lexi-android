package com.lexivip.lexi.order

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
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterOrderGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {
    private val dp65: Int by lazy { DimenUtil.dp2px(65.0) }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimen(item.cover, imageView, dp65, ImageSizeConfig.SIZE_P30X2)

        helper.setText(R.id.textViewName, item.product_name)
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp8, R.dimen.dp10), null, null, null)
        if (item.sale_price == 0.0) {
            textViewPrice.text = Util.doubleRoundHalfUp(item.price)
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = Util.doubleRoundHalfUp(item.sale_price)
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "¥" + Util.doubleRoundHalfUp(item.price)
        }

        val relativeLayoutGoodsItemExpress = helper.getView<RelativeLayout>(R.id.relativeLayoutGoodsItemExpress)
        val textViewSelectExpress = helper.getView<TextView>(R.id.textViewSelectExpress)

        val skuId: String? = item.map[item.fid]

        if (TextUtils.equals(item.rid, skuId)) {
            relativeLayoutGoodsItemExpress.visibility = View.VISIBLE
        } else {
            relativeLayoutGoodsItemExpress.visibility = View.GONE
        }

        //选择物流
        helper.addOnClickListener(R.id.relativeLayoutGoodsItemExpress)

        if (TextUtils.isEmpty(item.s_color) && !TextUtils.isEmpty(item.s_model)) {
            helper.setText(R.id.textViewSpec, item.s_model)
        } else if (!TextUtils.isEmpty(item.s_color) && TextUtils.isEmpty(item.s_model)) {
            helper.setText(R.id.textViewSpec, item.s_color)
        } else if (!TextUtils.isEmpty(item.s_color) && !TextUtils.isEmpty(item.s_model)) {
            helper.setText(R.id.textViewSpec, item.s_color + " / " + item.s_model)
        } else {
            helper.setText(R.id.textViewSpec, "")
        }

        helper.setText(R.id.textViewGoodsNum, "x${item.quantity}")

        //物流为空
        if (item.express == null) return

        val expressList = item.express
        for (express in expressList) {
            if (express.is_default) {
                helper.setText(R.id.textViewExpressName, "${express.express_name}")
                helper.setText(R.id.textViewExpressTime, "物流时长：${express.min_days}至${express.max_days}送达")
                break
            }
        }

        //物流模板下只有一个物流公司
        if (expressList.size == 1) {
            textViewSelectExpress.visibility = View.GONE
            relativeLayoutGoodsItemExpress.isEnabled = false
        } else {
            textViewSelectExpress.visibility = View.VISIBLE
            relativeLayoutGoodsItemExpress.isEnabled = true
        }

    }
}