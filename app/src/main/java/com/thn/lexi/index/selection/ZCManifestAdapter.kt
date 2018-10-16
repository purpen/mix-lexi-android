package com.thn.lexi.index.selection
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.LifeWillBean

class ZCManifestAdapter(layoutResId: Int) : BaseQuickAdapter<LifeWillBean, BaseViewHolder>(layoutResId) {
    private val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth()-DimenUtil.dp2px(40.0))/2 }
    private val size106 by lazy { DimenUtil.dp2px(106.0)}

    override fun convert(helper: BaseViewHolder, item: LifeWillBean) {

        helper.itemView.layoutParams = RelativeLayout.LayoutParams(sizeSpan2,ViewGroup.LayoutParams.WRAP_CONTENT)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(sizeSpan2,size106)
        GlideUtil.loadImageWithRadius(item.cover, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp4))

        helper.setText(R.id.textViewTitle0, item.title)
        helper.setText(R.id.textViewTitle1, item.description)
        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.setTextColor(Util.getColor(R.color.color_666))
        textViewName.text = item.user_name

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.cover, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp20))


    }
}