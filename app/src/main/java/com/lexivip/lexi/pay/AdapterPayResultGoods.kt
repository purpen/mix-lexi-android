package com.lexivip.lexi.pay
import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.*
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterPayResultGoods(@LayoutRes res: Int) : BaseQuickAdapter<PayResultBean.DataBean.OrdersBean.ItemsBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: PayResultBean.DataBean.OrdersBean.ItemsBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithFading(item.cover, imageView)

        helper.setText(R.id.textViewName, item.product_name)
        val textViewPrice = helper.getView<TextView>(R.id.textViewPrice)
        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)
        textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp8, R.dimen.dp10), null, null, null)
        if (item.sale_price == 0.0) {
            textViewPrice.text = "${item.price}"
            textViewOldPrice.visibility = View.GONE
        } else {
            textViewOldPrice.visibility = View.VISIBLE
            textViewPrice.text = "${item.sale_price}"
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥${item.price}"
        }


        helper.setText(R.id.textViewSpec, item.s_color + " / " + item.s_model)

        helper.setText(R.id.textViewGoodsNum, "x${item.quantity}")

        if (item.max_days==0){
            helper.setText(R.id.textViewExpressTime, "交货.${item.py_intro}")
        }else{
            helper.setText(R.id.textViewExpressTime, "交货.${item.min_days}-${item.max_days}送达")
        }
    }
}