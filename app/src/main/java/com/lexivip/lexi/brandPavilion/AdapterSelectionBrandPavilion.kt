package com.lexivip.lexi.brandPavilion
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class AdapterSelectionBrandPavilion(layoutResId: Int) : BaseQuickAdapter<SelectionBrandPavilionListBean.DataBean.HandpickStoreBean, BaseViewHolder>(layoutResId) {
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    private val dp75:Int by lazy { DimenUtil.dp2px(75.0) }
    override fun convert(helper: BaseViewHolder, item: SelectionBrandPavilionListBean.DataBean.HandpickStoreBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(item.logo, imageView,dp4,dp75,ImageSizeConfig.SIZE_P30X2)
        helper.setText(R.id.textViewName, item.name)
        helper.setText(R.id.textViewNum, "${item.store_products_counts}件商品")

    }
}