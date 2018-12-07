package com.lexivip.lexi.index.explore
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R

class CollectionGoodsAdapter(layoutResId: Int) : BaseQuickAdapter<GoodsCollectionBean.DataBean.CollectionsBean, BaseViewHolder>(layoutResId) {
    private val dp150:Int by lazy { DimenUtil.dp2px(150.0) }
    private val layoutParams:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp150,dp150) }
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    override fun convert(helper: BaseViewHolder, item: GoodsCollectionBean.DataBean.CollectionsBean) {
        helper.setText(R.id.textViewTitle0,item.name)
        helper.setText(R.id.textViewTitle1,item.sub_name)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = layoutParams
        GlideUtil.loadImageWithDimenAndRadius(item.cover,imageView,dp4,dp150,ImageSizeConfig.SIZE_P30X2)
    }
}