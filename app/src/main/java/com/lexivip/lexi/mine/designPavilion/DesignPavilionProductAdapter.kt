package com.lexivip.lexi.mine.designPavilion

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class DesignPavilionProductAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val dp90:Int by lazy { DimenUtil.dp2px(90.0) }
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = ViewGroup.LayoutParams(dp90,dp90)
        GlideUtil.loadImageWithDimenAndRadius(item,imageView,dp4,dp90,ImageSizeConfig.SIZE_P30X2)
    }
}