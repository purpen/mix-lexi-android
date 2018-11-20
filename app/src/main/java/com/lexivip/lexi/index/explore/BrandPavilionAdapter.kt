package com.lexivip.lexi.index.explore

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R

class BrandPavilionAdapter(layoutResId: Int) : BaseQuickAdapter<BrandPavilionListBean.DataBean.StoresBean, BaseViewHolder>(layoutResId) {
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val dp249: Int by lazy { DimenUtil.dp2px(249.0) }
    private val dp219: Int by lazy { DimenUtil.dp2px(219.0) }
    private val dp44: Int by lazy { DimenUtil.dp2px(44.0) }
    override fun convert(helper: BaseViewHolder, item: BrandPavilionListBean.DataBean.StoresBean) {
        val imageViewBg = helper.getView<ImageView>(R.id.imageViewBg)
        GlideUtil.loadImageWithBlurAndRadius(item.bgcover, imageViewBg, dp4, dp249, dp219)

        val imageViewShop = helper.getView<ImageView>(R.id.imageViewShop)
        GlideUtil.loadImageWithDimenAndRadius(item.logo, imageViewShop, dp4, dp44)

        helper.setText(R.id.textViewTitle, item.name)
        helper.setText(R.id.textViewCount, "${item.store_products_counts}件")

        val textViewFocus = helper.getView<TextView>(R.id.textViewFocus)

        if (item.is_followed) {
            textViewFocus.text = Util.getString(R.string.text_focused)
            textViewFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            textViewFocus.setBackgroundResource(R.drawable.bg_round_coloreff3f2)
            textViewFocus.setTextColor(Util.getColor(R.color.color_949ea6))
        } else {
            textViewFocus.text = Util.getString(R.string.text_focus)
            textViewFocus.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_focus_pavilion, 0, 0, 0)
            textViewFocus.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            textViewFocus.setTextColor(Util.getColor(android.R.color.white))
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BrandPavilionProductAdapter(R.layout.adapter_brand_pavilion_product)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter.setNewData(item.products_cover)

        helper.addOnClickListener(R.id.textViewFocus)
        helper.addOnClickListener(R.id.recyclerViewProducts)

        //跳转品牌馆主页
        adapter.setOnItemClickListener { _, _, _ ->
            PageUtil.jump2BrandPavilionActivity(item.rid)
        }

    }
}