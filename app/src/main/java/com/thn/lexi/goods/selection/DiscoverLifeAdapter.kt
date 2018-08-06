package com.thn.lexi.goods.selection

import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class DiscoverLifeAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {

        helper.setText(R.id.textViewName,item.name)
        helper.setText(R.id.textViewTitle1,item.store_name)
        helper.setText(R.id.textViewTitle2,item.features)

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        val resources = imageViewAvatar.resources
        GlideUtil.loadCircleImageWidthDimen(item.cover,imageViewAvatar,resources.getDimensionPixelSize(R.dimen.dp25))

        val imageView0 = helper.getView<ImageView>(R.id.imageView0)
        GlideUtil.loadImageWithRadius(item.cover,imageView0,resources.getDimensionPixelSize(R.dimen.dp5))

        val imageView1 = helper.getView<ImageView>(R.id.imageView1)
        GlideUtil.loadImageWithRadius(item.cover,imageView1,resources.getDimensionPixelSize(R.dimen.dp5))

        val imageView2 = helper.getView<ImageView>(R.id.imageView2)
        GlideUtil.loadImageWithRadius(item.cover,imageView2,resources.getDimensionPixelSize(R.dimen.dp5))
    }
}