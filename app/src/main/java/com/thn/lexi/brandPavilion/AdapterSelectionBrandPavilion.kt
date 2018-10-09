package com.thn.lexi.brandPavilion
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AdapterSelectionBrandPavilion(layoutResId: Int) : BaseQuickAdapter<SelectionBrandPavilionListBean.DataBean.HandpickStoreBean, BaseViewHolder>(layoutResId) {
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    override fun convert(helper: BaseViewHolder, item: SelectionBrandPavilionListBean.DataBean.HandpickStoreBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.logo, imageView,dp4)
        helper.setText(R.id.textViewName, item.name)
        helper.setText(R.id.textViewNum, "${item.store_products_counts}件商品")

    }
}