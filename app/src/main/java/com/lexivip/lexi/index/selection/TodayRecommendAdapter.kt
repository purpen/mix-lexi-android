package com.lexivip.lexi.index.selection

import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class TodayRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<TodayRecommendBean.DataBean.DailyRecommendsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: TodayRecommendBean.DataBean.DailyRecommendsBean) {

        helper.setText(R.id.textViewTitle,item.channel_name)
        helper.setText(R.id.textViewTitle1,item.recommend_title)
        helper.setText(R.id.textViewTitle2,item.recommend_description)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }
}