package com.lexivip.lexi.index.detail
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R

class AdapterGoodsDetail(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterGoodsDetail.MultipleItem, BaseViewHolder>(list) {
    private val imgW:Int by lazy { ScreenUtil.getScreenWidth()-DimenUtil.dp2px(30.0) }
    private val dp15:Int by lazy { DimenUtil.dp2px(15.0) }
    private val imgH:Int by lazy { DimenUtil.dp2px(220.0) }
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
                val textView = helper.getView<TextView>(R.id.textView)
                textView.text = Html.fromHtml(item.content.content)
            }

            MultipleItem.IMAGE_ITEM_TYPE -> {
                val imageView = helper.getView<ImageView>(R.id.imageView)
                imageView.setPadding(dp15,0,dp15,0)
                GlideUtil.loadImageAdjustImageViewDimen(item.content.content, imageView,0,imgW,imgH)
            }
        }
    }
}