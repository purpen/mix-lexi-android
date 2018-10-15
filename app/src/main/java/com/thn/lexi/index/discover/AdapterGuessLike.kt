package com.thn.lexi.index.discover
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

class AdapterGuessLike(layoutResId: Int) : BaseQuickAdapter<LifeWillBean, BaseViewHolder>(layoutResId) {
    private val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth()-DimenUtil.dp2px(40.0))/2 }
    private val size106 by lazy { DimenUtil.dp2px(106.0)}
    private val size20 by lazy { DimenUtil.dp2px(20.0)}
    private val size4 by lazy { DimenUtil.dp2px(4.0)}

    override fun convert(helper: BaseViewHolder, item: LifeWillBean) {

        helper.itemView.layoutParams = RelativeLayout.LayoutParams(sizeSpan2,ViewGroup.LayoutParams.WRAP_CONTENT)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(sizeSpan2,size106)
        GlideUtil.loadImageWithRadius(item.cover, imageView, size4)

        val textViewTitle0 = helper.getView<TextView>(R.id.textViewTitle0)

        textViewTitle0.text = item.channel_name

        when(item.channel_name){
            "种草笔记" ->{
                textViewTitle0.setTextColor(Util.getColor(R.color.color_75ab9a))
            }
            "手作教学" ->{
                textViewTitle0.setTextColor(Util.getColor(R.color.color_e3b395))
            }

            "创作人故事" ->{
                textViewTitle0.setTextColor(Util.getColor(R.color.color_829d7a))
            }

            "生活记事" ->{
                textViewTitle0.setTextColor(Util.getColor(R.color.color_8c7a6e))
            }
        }

        helper.setText(R.id.textViewTitle1, item.title)

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.cover, imageViewAvatar, size20)


    }
}