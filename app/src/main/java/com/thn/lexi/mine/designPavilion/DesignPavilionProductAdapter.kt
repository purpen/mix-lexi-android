package com.thn.lexi.mine.designPavilion

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class DesignPavilionProductAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val size:Int = DimenUtil.getDimensionPixelSize(R.dimen.dp90)
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = ViewGroup.LayoutParams(size,size)
        GlideUtil.loadImageWithRadius(item,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }
}