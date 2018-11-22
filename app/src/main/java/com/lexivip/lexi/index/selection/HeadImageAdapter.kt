package com.lexivip.lexi.index.selection

import android.support.annotation.LayoutRes
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import de.hdodenhof.circleimageview.CircleImageView

class HeadImageAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val dp25:Int by lazy { DimenUtil.dp2px(25.0) }
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<CircleImageView>(R.id.circleImageView)
        GlideUtil.loadCircleImageWidthDimen(item,imageView,dp25,ImageSizeConfig.SIZE_AVA)
    }
}