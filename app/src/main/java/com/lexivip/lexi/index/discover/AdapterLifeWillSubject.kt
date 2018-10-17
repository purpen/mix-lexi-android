package com.lexivip.lexi.index.discover
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterLifeWillSubject(layoutResId: Int) : BaseQuickAdapter<LifeWillSubjectBean, BaseViewHolder>(layoutResId) {
    private val dp100 = DimenUtil.getDimensionPixelSize(R.dimen.dp100)
    private val dp4 = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
    override fun convert(helper: BaseViewHolder, item: LifeWillSubjectBean) {
        helper.itemView.layoutParams = ViewGroup.LayoutParams(dp100,ViewGroup.LayoutParams.WRAP_CONTENT)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(dp100,dp100)
        GlideUtil.loadImageWithRadius(item.imageId,imageView,dp4)

        helper.setText(R.id.textViewTitle,item.title)
    }
}