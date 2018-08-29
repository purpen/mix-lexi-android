package com.thn.lexi.mine

import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AdapterDynamicAdapter(@LayoutRes res: Int): BaseQuickAdapter<String, BaseViewHolder>(res) {
    private val size:Int = DimenUtil.getDimensionPixelSize(R.dimen.dp90)
    private val bounds:Rect by lazy { Rect(0,0,DimenUtil.getDimensionPixelSize(R.dimen.dp22),DimenUtil.getDimensionPixelSize(R.dimen.dp20)) }
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp30))
        helper.setText(R.id.textViewName,"")
    }
}