package com.lexivip.lexi.index.selection

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R

class DiscoverLifeProductAdapter(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<DiscoverLifeProductAdapter.MultipleItem, BaseViewHolder>(list) {
    private val size162:Int by lazy { DimenUtil.dp2px(162.0) }
    private val size80:Int by lazy { DimenUtil.dp2px(80.0) }
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


    override fun convert(helper: BaseViewHolder, item: DiscoverLifeProductAdapter.MultipleItem) {

        var layoutParams: ViewGroup.LayoutParams
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)

        if (item.itemType == MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = ViewGroup.LayoutParams(size162,size162)
            GlideUtil.loadImageWithDimenAndRadius(item.str,imageView,0,size162)
        } else {
            layoutParams = ViewGroup.LayoutParams(size80, size80)
            GlideUtil.loadImageWithDimenAndRadius(item.str,imageView,0,size80)
        }
        relativeLayout.layoutParams = layoutParams
    }
}