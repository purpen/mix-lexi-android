package com.lexivip.lexi.index.selection

import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class TodayRecommendAdapter(layoutResId: Int) : BaseQuickAdapter<TodayRecommendBean.DataBean.DailyRecommendsBean, BaseViewHolder>(layoutResId) {

    private val dp150: Int by lazy { DimenUtil.dp2px(150.0) }
    private val dp115: Int by lazy { DimenUtil.dp2px(115.0) }
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }

    override fun convert(helper: BaseViewHolder, item: TodayRecommendBean.DataBean.DailyRecommendsBean) {

        helper.setText(R.id.textViewTitle, item.recommend_label)
        helper.setText(R.id.textViewTitle1, item.recommend_title)
        helper.setText(R.id.textViewTitle2, item.recommend_description)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView, dp4, dp150, dp115,ImageSizeConfig.SIZE_P315X236)
    }
}