package com.lexivip.lexi.mine.dynamic
import android.support.annotation.LayoutRes
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class ShopWindowDynamicAdapter(@LayoutRes res: Int) : BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val dp106: Int by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(40.0))/3 }
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = ViewGroup.LayoutParams(dp106, dp106)
        GlideUtil.loadImageWithDimen(item, imageView, dp106, ImageSizeConfig.SIZE_P30X2)
    }
}