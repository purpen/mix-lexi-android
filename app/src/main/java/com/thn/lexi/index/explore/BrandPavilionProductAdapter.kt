package com.thn.lexi.index.explore

import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class BrandPavilionProductAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithFading(item,imageView)
    }
}