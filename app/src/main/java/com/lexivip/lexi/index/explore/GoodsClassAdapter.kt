package com.lexivip.lexi.index.explore

import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R

class GoodsClassAdapter(layoutResId: Int) : BaseQuickAdapter<GoodsClassBean.DataBean.CategoriesBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: GoodsClassBean.DataBean.CategoriesBean) {
        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewNum,""+item.id)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadCircleImageWidthDimen(item.cover,imageView,AppApplication.getContext().resources.getDimensionPixelSize(R.dimen.dp40))
    }
}