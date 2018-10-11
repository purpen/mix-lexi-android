package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean

class AdapterHotGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.setText(R.id.textView0, item.name)

//        val textView2 = helper.getView<TextView>(R.id.textView2)

        if (item.real_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.real_price}")
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            helper.setText(R.id.textView1, "￥${item.real_sale_price}")
        }

        helper.setText(R.id.textView3, "喜欢 +${item.like_count}")

        helper.setText(R.id.textViewEarn, "赚 ￥${item.commission_price}")

        val imageViewStatus = helper.getView<ImageView>(R.id.imageViewStatus)
        if (item.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        val imageViewFreeExpress = helper.getView<ImageView>(R.id.imageViewFreeExpress)
        if (item.is_free_postage) {
            imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            imageViewFreeExpress.visibility = View.VISIBLE
        }


        val imageView = helper.getView<ImageView>(R.id.imageViewGoods)
        GlideUtil.loadImageWithRadius(item.cover, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp4))


        //卖
        helper.addOnClickListener(R.id.textView4)

        //上架
        helper.addOnClickListener(R.id.textView5)

    }
}