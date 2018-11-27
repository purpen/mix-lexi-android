package com.lexivip.lexi.index.selection
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterSelectionBanner(@LayoutRes res: Int) : BaseQuickAdapter<String, BaseViewHolder>(res){
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private lateinit var parent:ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        this.parent = parent
        return super.onCreateViewHolder(parent, viewType)
    }
    private val mPagePadding = 20
    private val mShowLeftCardWidth = 15

    override fun convert(helper: BaseViewHolder, item: String) {
        val lp = helper.itemView.layoutParams as RecyclerView.LayoutParams
        lp.width =  parent.width - DimenUtil.dp2px((2*mPagePadding+mShowLeftCardWidth).toDouble())
        helper.itemView.layoutParams = lp

        val padding = DimenUtil.dp2px(mPagePadding.toDouble())

        helper.itemView.setPadding(padding, 0, padding, 0)
        setViewMargin(helper.itemView, 0, 0, 0, 0)
        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item,imageView,dp4)
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }

}