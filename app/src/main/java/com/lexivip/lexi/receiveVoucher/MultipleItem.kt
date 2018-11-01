package com.lexivip.lexi.receiveVoucher

import com.chad.library.adapter.base.entity.MultiItemEntity

class MultipleItem(var bean: VoucherBrandBean.DataBean.CouponsBean, private var itemType: Int, spanSize:Int) : MultiItemEntity {
    var spanSize:Int =spanSize
    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        const val ITEM_TYPE_SPAN2 = 0x0010
        const val ITEM_TYPE_SPAN1 = 0x0011
        const val ITEM_SPAN2_SIZE = 1
        const val ITEM_SPAN1_SIZE = 2
    }
}