package com.thn.lexi.search

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean

class AdapterSearchGoods(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterSearchGoods.MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.ITEM_TYPE_SPAN2, R.layout.adapter_editor_recommend)
        addItemType(MultipleItem.ITEM_TYPE_SPAN1, R.layout.adapter_editor_recommend)
    }

    class MultipleItem(var product: ProductBean, private var itemType: Int,spanSize:Int) : MultiItemEntity {
        public var spanSize:Int =spanSize
        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val ITEM_TYPE_SPAN2 = 0x0010
            const val ITEM_TYPE_SPAN1 = 0x0011
            const val ITEM_SPAN2_SIZE = 2
            const val ITEM_SPAN1_SIZE = 1
        }
    }


    override fun convert(helper: BaseViewHolder, item: AdapterSearchGoods.MultipleItem) {
        val product = item.product
        val layoutParams: ViewGroup.LayoutParams
//        val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val sizeScreen = ScreenUtil.getScreenWidth()-DimenUtil.dp2px(30.0)
        val sizeSmall = ((ScreenUtil.getScreenWidth()-DimenUtil.dp2px(40.0))*0.5).toInt()

        if (item.itemType == MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = RelativeLayout.LayoutParams(sizeScreen,sizeScreen)
        } else {
            layoutParams = RelativeLayout.LayoutParams(sizeSmall, sizeSmall)
        }
        imageView.layoutParams = layoutParams

        GlideUtil.loadImageWithFading(product.cover, imageView)

        helper.setText(R.id.textViewTitle,product.name)

        val textViewOldPrice =helper.getView<TextView>(R.id.textViewOldPrice)

        if (product.real_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${product.real_price}")
            textViewOldPrice.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${product.real_sale_price}")
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥" + product.real_price
        }

    }
}