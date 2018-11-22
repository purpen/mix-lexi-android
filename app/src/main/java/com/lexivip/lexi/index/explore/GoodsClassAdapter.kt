package com.lexivip.lexi.index.explore

import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class GoodsClassAdapter(layoutResId: Int) : BaseQuickAdapter<GoodsClassBean.DataBean.CategoriesBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: GoodsClassBean.DataBean.CategoriesBean) {
        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewNum,""+item.browse_count)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimen(item.cover,imageView,DimenUtil.dp2px(40.0),ImageSizeConfig.SIZE_AVA)
    }
}