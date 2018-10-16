package com.thn.lexi.index.discover
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.thn.lexi.R

class AdapterArticleDetail(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterArticleDetail.MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.TEXT_ITEM_TYPE, R.layout.adapter_goods_detail_text)
        addItemType(MultipleItem.IMAGE_ITEM_TYPE, R.layout.adapter_article_detail_image)
        addItemType(MultipleItem.PRODUCT_ITEM_TYPE, R.layout.adapter_goods_detail_image)
    }

    class MultipleItem(var content: ArticleDetailBean.DataBean.DealContentBean, private var itemType: Int) : MultiItemEntity {

        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val TEXT_ITEM_TYPE = 0x0010
            const val IMAGE_ITEM_TYPE = 0x0011
            const val PRODUCT_ITEM_TYPE = 0x0012
        }
    }


    override fun convert(helper: BaseViewHolder, item: AdapterArticleDetail.MultipleItem) {

        when (item.itemType) {
            MultipleItem.TEXT_ITEM_TYPE -> {
                val textView = helper.getView<TextView>(R.id.textView)
                textView.text = Html.fromHtml(item.content.content)
            }

            MultipleItem.IMAGE_ITEM_TYPE -> {
                val imageView = helper.getView<ImageView>(R.id.imageView)
                GlideUtil.loadImageWithFading(item.content.content, imageView)
            }
        }
    }
}