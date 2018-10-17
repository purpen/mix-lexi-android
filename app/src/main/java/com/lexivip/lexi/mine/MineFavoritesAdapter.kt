package com.lexivip.lexi.mine
import android.support.annotation.LayoutRes
import android.widget.ImageView
import android.widget.LinearLayout
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.index.selection.GoodsData

class MineFavoritesAdapter(@LayoutRes res: Int) : BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: GoodsData.DataBean.ProductsBean) {
        helper.setText(R.id.textView0, item.name)
        helper.setText(R.id.textView1, "${item.sale_price}")
        helper.setText(R.id.textView2, "喜欢 +329")
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, imageView.context.resources.getDimensionPixelSize(R.dimen.dp385))
        imageView.layoutParams = params
        GlideUtil.loadImage(item.cover,imageView)
    }
}