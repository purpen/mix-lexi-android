package com.lexivip.lexi.index.selection
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
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.LifeWillBean

class ZCManifestAdapter(layoutResId: Int) : BaseQuickAdapter<LifeWillBean, BaseViewHolder>(layoutResId) {
    private val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth()-DimenUtil.dp2px(40.0))/2 }
    private val dp106 by lazy { DimenUtil.dp2px(106.0)}
    private val dp20 by lazy { DimenUtil.dp2px(20.0)}
    private val dp4 by lazy { DimenUtil.dp2px(4.0)}
    private val layoutParams by lazy { RelativeLayout.LayoutParams(sizeSpan2,dp106)}
    private val itemLayoutParams by lazy { RelativeLayout.LayoutParams(sizeSpan2,ViewGroup.LayoutParams.WRAP_CONTENT)}

    override fun convert(helper: BaseViewHolder, item: LifeWillBean) {

        helper.itemView.layoutParams = itemLayoutParams

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = layoutParams
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView,dp4,sizeSpan2,dp106)

        helper.setText(R.id.textViewTitle0, item.title)
        helper.setText(R.id.textViewTitle1, item.description)
        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.setTextColor(Util.getColor(R.color.color_666))
        textViewName.text = item.user_name

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.cover, imageViewAvatar, dp20)


    }
}