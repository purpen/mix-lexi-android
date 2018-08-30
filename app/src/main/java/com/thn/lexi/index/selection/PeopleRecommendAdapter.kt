package com.thn.lexi.index.selection
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean

class PeopleRecommendAdapter(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<PeopleRecommendAdapter.MultipleItem, BaseViewHolder>(list) {

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
        val resources = imageView.resources

        if (item.itemType == MultipleItem.ITEM_TYPE_SPAN2) {
            layoutParams = RelativeLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp160), resources.getDimensionPixelSize(R.dimen.dp128))
            imageView.layoutParams = layoutParams
        } else {
            val pixelSize = resources.getDimensionPixelSize(R.dimen.dp104)
            layoutParams = RelativeLayout.LayoutParams(pixelSize, pixelSize)
            imageView.layoutParams = layoutParams
        }

        GlideUtil.loadImageWithRadius(itemProduct.cover, imageView, resources.getDimensionPixelSize(R.dimen.dp5))


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

        val textViewOldPrice =helper.getView<TextView>(R.id.textViewOldPrice)

        if (itemProduct.real_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${itemProduct.real_price}")
            textViewOldPrice.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${itemProduct.real_sale_price}")
            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOldPrice.text = "￥" + itemProduct.real_price
        }
    }
}