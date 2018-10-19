package com.lexivip.lexi.mine.like
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterLikeGoods(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    private val dp100:Int by lazy { DimenUtil.dp2px(100.0) }
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(item.cover,imageView,dp4,dp100)
    }
}