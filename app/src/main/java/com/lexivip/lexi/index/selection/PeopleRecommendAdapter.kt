package com.lexivip.lexi.index.selection

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class PeopleRecommendAdapter(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<PeopleRecommendAdapter.MultipleItem, BaseViewHolder>(list) {
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(40.0)) / 2 }

    private val sizeSpan1 by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(48.0)) / 3 }

    init {
        addItemType(MultipleItem.ITEM_TYPE_SPAN2, R.layout.adapter_editor_recommend)
        addItemType(MultipleItem.ITEM_TYPE_SPAN3, R.layout.adapter_editor_recommend)
    }

    class MultipleItem(var product: ProductBean, private var itemType: Int, var spanSize: Int) : MultiItemEntity {

        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val ITEM_TYPE_SPAN2 = 0x0010
            const val ITEM_TYPE_SPAN3 = 0x0011
            const val ITEM_SPAN2_SIZE = 2
            const val ITEM_SPAN3_SIZE = 3
        }
    }


    override fun convert(helper: BaseViewHolder, item: PeopleRecommendAdapter.MultipleItem) {

        var itemProduct = item.product

        var layoutParams: RelativeLayout.LayoutParams


        val imageView = helper.getView<ImageView>(R.id.imageView)

        if (item.itemType == MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = RelativeLayout.LayoutParams(sizeSpan2, sizeSpan2)
            imageView.layoutParams = layoutParams
            GlideUtil.loadImageWithDimenAndRadius(itemProduct.cover, imageView, dp4, sizeSpan2)
        } else {
            layoutParams = RelativeLayout.LayoutParams(sizeSpan1, sizeSpan1)
            imageView.layoutParams = layoutParams
            GlideUtil.loadImageWithDimenAndRadius(itemProduct.cover, imageView, dp4, sizeSpan1)
        }


        val imageViewStatus = helper.getView<View>(R.id.imageViewStatus)
        if (itemProduct.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        val imageViewExpress = helper.getView<View>(R.id.imageViewExpress)

        if (itemProduct.is_free_postage) {
            imageViewExpress.visibility = View.VISIBLE
        } else {
            imageViewExpress.visibility = View.GONE
        }

        helper.setText(R.id.textViewTitle, itemProduct.name)

        val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)

        if (itemProduct.min_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${itemProduct.min_price}")
            textViewOldPrice.visibility = View.GONE
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${itemProduct.min_sale_price}")
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥" + itemProduct.min_price
        }
    }
}