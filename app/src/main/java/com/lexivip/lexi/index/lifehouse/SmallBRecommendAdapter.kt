package com.lexivip.lexi.index.lifehouse
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.CustomImageSpan
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class SmallBRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    private val pixelSize: Int by lazy { DimenUtil.dp2px(100.0) }
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val layoutParams: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(pixelSize, RelativeLayout.LayoutParams.WRAP_CONTENT) }
    private val imageViewlayoutParams: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(pixelSize, pixelSize) }

    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.itemView.layoutParams = layoutParams

        val imageViewStatus = helper.getView<View>(R.id.imageViewStatus)
        if (item.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        val textViewTitle = helper.getView<TextView>(R.id.textViewTitle)

        if (item.is_free_postage) {
            val drawable = Util.getDrawableWidthPxDimen(R.mipmap.icon_free_express, DimenUtil.dp2px(20.0), DimenUtil.dp2px(12.0))
            val span = CustomImageSpan(drawable)
            val spannable = SpannableString("   " + item.name)
            spannable.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            textViewTitle.text = spannable
        } else {
            textViewTitle.text = item.name
        }

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = imageViewlayoutParams
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView, dp4, pixelSize, ImageSizeConfig.SIZE_P30X2)


        if (item.min_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${item.min_price}")
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            helper.setText(R.id.textViewPrice, "${item.min_sale_price}")
        }

        val textViewLike = helper.getView<TextView>(R.id.textViewLike)
        if (item.like_count > 0) {
            val numberString = Util.getNumberString(item.like_count)
            textViewLike.text = "喜欢 +$numberString"
            textViewLike.visibility = View.VISIBLE
        } else {
            textViewLike.visibility = View.GONE
        }
    }
}