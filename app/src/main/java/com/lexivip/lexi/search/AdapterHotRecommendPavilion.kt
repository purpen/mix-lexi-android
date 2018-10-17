package com.lexivip.lexi.search

import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterHotRecommendPavilion(layoutResId: Int) : BaseQuickAdapter<SearchHotRecommendPavilionBean.DataBean.HotRecommendsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: SearchHotRecommendPavilionBean.DataBean.HotRecommendsBean) {
        helper.setText(R.id.textViewTitle, item.recommend_title)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        if (helper.adapterPosition == 0) {
            GlideUtil.loadImageWithDimen(item.coverId, imageView, DimenUtil.dp2px(45.0))
        } else {
            GlideUtil.loadCircleImageWidthDimen(item.recommend_cover, imageView, DimenUtil.dp2px(45.0))
        }
    }
}