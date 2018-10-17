package com.lexivip.lexi.index.explore

import android.support.annotation.DrawableRes
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class ExploreAdapter(data: List<ExploreAdapter.MultipleItem>) : BaseMultiItemQuickAdapter<ExploreAdapter.MultipleItem, BaseViewHolder>(data) {

    init {
//        addItemType(MultipleItem.IMAGE, R.layout.item_image)
//        addItemType(MultipleItem.IMAGE_TEXT, R.layout.item_recyclerview_product2)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem?) {

    }


    class MultipleItem : MultiItemEntity {
        private var itemType: Int = 0
//        var item: CustomMenuBean
        var iconId: Int = 0

        constructor(itemType: Int) {
            this.itemType = itemType
//            this.item = bean
//            this.iconId = bean.iconId
        }

        constructor(itemType: Int, @DrawableRes iconId: Int) {
            this.itemType = itemType
            this.iconId = iconId
        }


        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            val IMAGE = 0x0010
            val IMAGE_TEXT = 0x0011
        }
    }
}