package com.thn.lexi.index.explore
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class CollectionGoodsAdapter(layoutResId: Int) : BaseQuickAdapter<GoodsCollectionBean.DataBean.CollectionsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: GoodsCollectionBean.DataBean.CollectionsBean) {
        helper.setText(R.id.textViewTitle0,item.name)
        helper.setText(R.id.textViewTitle0,item.sub_name)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }
}