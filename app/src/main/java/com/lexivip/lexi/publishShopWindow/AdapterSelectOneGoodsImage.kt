package com.lexivip.lexi.publishShopWindow
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class AdapterSelectOneGoodsImage(layoutResId: Int) : BaseQuickAdapter<SelectGoodsImageBean.DataBean.ImagesBean, BaseViewHolder>(layoutResId) {
    private val imageSize: Int by lazy { DimenUtil.dp2px(80.0)}
    override fun convert(helper: BaseViewHolder, item: SelectGoodsImageBean.DataBean.ImagesBean) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val imageViewCheck = helper.getView<ImageView>(R.id.imageViewCheck)
        GlideUtil.loadImageWithDimenAndRadius(item.view_url, imageView, 0,imageSize,ImageSizeConfig.SIZE_P30X2)
        if (item.selected){
            imageViewCheck.visibility = View.VISIBLE
        }else{
            imageViewCheck.visibility = View.GONE
        }
    }
}