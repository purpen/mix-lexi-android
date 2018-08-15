package com.thn.lexi.index.selection
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class ZCManifestAdapter(layoutResId: Int) : BaseQuickAdapter<ZCManifestBean.DataBean.LifeRecordsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ZCManifestBean.DataBean.LifeRecordsBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item.cover, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp5))

        helper.setText(R.id.textViewTitle0, item.title)
        helper.setText(R.id.textViewTitle1, item.description)

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.cover, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp20))


    }
}