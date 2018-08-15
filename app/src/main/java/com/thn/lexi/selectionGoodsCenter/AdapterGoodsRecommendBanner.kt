package com.thn.lexi.selectionGoodsCenter
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AdapterGoodsRecommendBanner(@LayoutRes res: Int) : BaseQuickAdapter<String, BaseViewHolder>(res){
    private lateinit var parent:ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        this.parent = parent
        return super.onCreateViewHolder(parent, viewType)
    }
    private val mPagePadding = 20
    private val mShowLeftCardWidth = 15

    override fun convert(helper: BaseViewHolder, item: String) {
        val lp = helper.itemView.layoutParams as RecyclerView.LayoutParams
        lp.width =  parent.width - DimenUtil.dp2px((2 * (mPagePadding + mShowLeftCardWidth)).toDouble())
        helper.itemView.layoutParams = lp

        val padding = DimenUtil.dp2px(mPagePadding.toDouble())

        helper.itemView.setPadding(padding, 0, padding, 0)
        val leftMarin = if (helper.adapterPosition == 0) padding + DimenUtil.dp2px(mShowLeftCardWidth.toDouble()) else 0
        val rightMarin = if (helper.adapterPosition == itemCount - 1) padding + DimenUtil.dp2px(mShowLeftCardWidth.toDouble()) else 0
        setViewMargin(helper.itemView, 0, 0, 0, 0)

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithRadius(item,imageView,imageView.resources.getDimensionPixelSize(R.dimen.dp5))
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }

}