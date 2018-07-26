package com.thn.lexi.goods.selection

import android.support.annotation.LayoutRes
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import de.hdodenhof.circleimageview.CircleImageView

class HeadImageAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder?, item: String) {
        val imageView = helper?.getView<CircleImageView>(R.id.circleImageView)
        GlideUtil.loadImageWithFading(item,imageView)
    }
}