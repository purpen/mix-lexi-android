package com.lexivip.lexi.index.explore
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class CollectionGoodsAdapter(layoutResId: Int) : BaseQuickAdapter<GoodsCollectionBean.DataBean.CollectionsBean, BaseViewHolder>(layoutResId) {
    private val dp150:Int by lazy { DimenUtil.dp2px(150.0) }
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    override fun convert(helper: BaseViewHolder, item: GoodsCollectionBean.DataBean.CollectionsBean) {
        helper.setText(R.id.textViewTitle0,item.name)
        helper.setText(R.id.textViewTitle1,item.sub_name)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithDimenAndRadius(item.cover,imageView,dp4,dp150)
    }
}