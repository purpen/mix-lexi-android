package com.lexivip.lexi.mine.like.likeShopWindow

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean

class AdapterLikeShowWindow(layoutResId: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(layoutResId) {
    private val dp124: Int by lazy { ScreenUtil.getScreenWidth() / 3 }
    private val dp250: Int by lazy { dp124 * 2 + 1 }
    private val dp2: Int by lazy { DimenUtil.dp2px(2.0) }

    private val layoutParams250: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp250, dp250) }
    private val layoutParams31: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
    private val layoutParams32: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {

        helper.setText(R.id.textViewTitle, item.title)

        val productCovers = item.product_covers

        // 设置3张产品图
        if (productCovers.isEmpty()) return

        val textView = helper.getView<TextView>(R.id.textView)
        val list = ArrayList<String>()

        val size = item.product_count

        if (size <= 3) {
            list.addAll(productCovers)
            textView.visibility = View.GONE
        } else {
            val subList = productCovers.subList(0, 3)
            list.addAll(subList)
            textView.visibility = View.VISIBLE
            textView.text = "+" + (size - 3)
        }

        val imageView30 = helper.getView<ImageView>(R.id.imageView30)
        val imageView31 = helper.getView<ImageView>(R.id.imageView31)
        val imageView32 = helper.getView<ImageView>(R.id.imageView32)
        val relativeLayoutImage32 = helper.getView<RelativeLayout>(R.id.relativeLayoutImage32)
        imageView30.layoutParams = layoutParams250
        imageView31.layoutParams = layoutParams31
        layoutParams31.addRule(RelativeLayout.END_OF, R.id.imageView30)
        layoutParams31.marginStart = dp2

        layoutParams32.addRule(RelativeLayout.BELOW, R.id.imageView31)
        layoutParams32.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView31)
        layoutParams32.topMargin = dp2 / 2
        relativeLayoutImage32.layoutParams = layoutParams32

        GlideUtil.loadImageWithDimenAndRadius(list[0], imageView30, 0, dp250, dp250,ImageSizeConfig.SIZE_P500)
        GlideUtil.loadImageWithDimenAndRadius(list[1], imageView31, 0, dp124,ImageSizeConfig.SIZE_P30X2)
        GlideUtil.loadImageWithDimenAndRadius(list[2], imageView32, 0, dp124,ImageSizeConfig.SIZE_P30X2)
    }
}