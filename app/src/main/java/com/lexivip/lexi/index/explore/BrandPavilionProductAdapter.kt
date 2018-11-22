package com.lexivip.lexi.index.explore
import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class BrandPavilionProductAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val dp70: Int by lazy { DimenUtil.dp2px(70.0) }
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(item,imageView,0,dp70,ImageSizeConfig.SIZE_P30X2)
    }
}