package com.thn.lexi.goods.selection

import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class TodayRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewTitle1,item.store_name)
        helper.setText(R.id.textViewTitle2,item.features)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}