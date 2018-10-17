package com.lexivip.lexi.discoverLifeAesthetics

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R

class ShowWindowProductAdapter(list: List<MultipleItem>, private val size: Int) : BaseMultiItemQuickAdapter<ShowWindowProductAdapter.MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.ITEM_TYPE_SPAN2, R.layout.adapter_discover_life_product)
        addItemType(MultipleItem.ITEM_TYPE_SPAN1, R.layout.adapter_discover_life_product)
    }

    class MultipleItem(var str: String, private var itemType: Int, var spanSize: Int) : MultiItemEntity {

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


    override fun convert(helper: BaseViewHolder, item: ShowWindowProductAdapter.MultipleItem) {

        val layoutParams: ViewGroup.LayoutParams
        val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val size250 = DimenUtil.getDimensionPixelSize(R.dimen.dp250)
        val size124 = DimenUtil.getDimensionPixelSize(R.dimen.dp124)
        if (item.itemType == MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = ViewGroup.LayoutParams(size250, size250)
        } else {
            layoutParams = ViewGroup.LayoutParams(size124, size124)
        }
        relativeLayout.layoutParams = layoutParams
        GlideUtil.loadImageWithFading(item.str, imageView)

        val textView = helper.getView<TextView>(R.id.textView)

        if (size > 3 && helper.adapterPosition == 2) {
            textView.visibility = View.VISIBLE
            textView.text = "+${size - 3}"
        } else {
            textView.visibility = View.GONE
        }
    }
}