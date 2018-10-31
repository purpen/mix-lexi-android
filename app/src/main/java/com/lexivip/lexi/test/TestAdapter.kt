package com.lexivip.lexi.test

import com.basemodule.tools.LogUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class TestAdapter(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<TestAdapter.MultipleItem, BaseViewHolder>(list) {
    init {
        addItemType(MultipleItem.ITEM_TYPE_SPAN2, R.layout.aaatext)
        addItemType(MultipleItem.ITEM_TYPE_SPAN1, R.layout.aaatext)
    }
    override fun convert(helper: BaseViewHolder, item: TestAdapter.MultipleItem) {
        //helper.setText(R.id.textView,item.product.name)
        LogUtil.e(item.product.name)
        helper.setText(R.id.textView,item.product.name)
    }

    class MultipleItem(var product: ProductBean, private var itemType: Int, spanSize:Int) : MultiItemEntity {
        var spanSize:Int =spanSize
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
}