package com.thn.lexi.index.detail
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.thn.lexi.R

class AdapterGoodsDetail(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterGoodsDetail.MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.TEXT_ITEM_TYPE, R.layout.adapter_goods_detail_text)
        addItemType(MultipleItem.IMAGE_ITEM_TYPE, R.layout.adapter_goods_detail_image)
    }

    class MultipleItem(var content: GoodsAllDetailBean.DataBean.DealContentBean, private var itemType: Int) : MultiItemEntity {

        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val TEXT_ITEM_TYPE = 0x0010
            const val IMAGE_ITEM_TYPE = 0x0011
        }
    }


    override fun convert(helper: BaseViewHolder, item: AdapterGoodsDetail.MultipleItem) {

        when (item.itemType) {
            MultipleItem.TEXT_ITEM_TYPE -> {
                helper.setText(R.id.textView, item.content.content)
            }

            MultipleItem.IMAGE_ITEM_TYPE -> {
                val imageView = helper.getView<ImageView>(R.id.imageView)
                GlideUtil.loadImageWithFading(item.content, imageView)
            }
        }
    }
}