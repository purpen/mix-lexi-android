package com.thn.lexi.mine.like
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean

class AdapterLikeGoods(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp5))
    }
}