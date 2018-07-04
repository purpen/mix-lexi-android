package com.thn.lexi.goods

import android.support.annotation.LayoutRes
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import de.hdodenhof.circleimageview.CircleImageView

class HeadImageAdapter(@LayoutRes res: Int): BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder?, item: GoodsData.DataBean.ProductsBean?) {
        val imageView = helper?.getView<CircleImageView>(R.id.circleImageView)
        GlideUtil.loadImageWithFading(item?.cover,imageView)
    }
}