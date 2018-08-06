package com.thn.lexi.goods.selection

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class ZCManifestAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover, imageView, imageView.resources.getDimensionPixelSize(R.dimen.dp5))

        helper.setText(R.id.textViewTitle0, item.store_name)
        helper.setText(R.id.textViewTitle1, item.features)

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.cover, imageViewAvatar, imageViewAvatar.resources.getDimensionPixelSize(R.dimen.dp20))


    }
}