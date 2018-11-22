package com.lexivip.lexi.publishShopWindow
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterGridImage(layoutResId: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(layoutResId) {

    private val imageSize: Int by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(2.0)) / 3 }
    private val layoutParams: ViewGroup.LayoutParams by lazy { ViewGroup.LayoutParams(imageSize,imageSize) }

    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = layoutParams
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView, 0,imageSize,ImageSizeConfig.SIZE_P30X2)
    }
}