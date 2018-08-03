package com.thn.lexi.goods.selection
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class PeopleRecommendAdapter(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<PeopleRecommendAdapter.MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.ITEM_TYPE_SPAN2, R.layout.adapter_editor_recommend)
        addItemType(MultipleItem.ITEM_TYPE_SPAN3, R.layout.adapter_editor_recommend)
    }

    class MultipleItem(var product: EditorRecommendBean.DataBean.ProductsBean, private var itemType: Int, var spanSize: Int) : MultiItemEntity {

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
        helper.setText(R.id.textViewPrice, itemProduct.min_sale_price)

    }
}