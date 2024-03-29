package com.lexivip.lexi.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.BrandPavilionBean
import com.lexivip.lexi.mine.designPavilion.DesignPavilionProductAdapter

class AdapterSearchBrandPavilion(layoutResId: Int) : BaseQuickAdapter<BrandPavilionBean, BaseViewHolder>(layoutResId) {
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val dp45: Int by lazy { DimenUtil.dp2px(45.0) }
    override fun convert(helper: BaseViewHolder, item: BrandPavilionBean) {

        val imageViewShop = helper.getView<ImageView>(R.id.imageViewShop)

        GlideUtil.loadImageWithDimenAndRadius(item.logo, imageViewShop, dp4, dp45,ImageSizeConfig.SIZE_AVA)

        helper.setText(R.id.textViewTitle, item.name)

        helper.setText(R.id.textViewCount, "${item.product_count}件商品")

        val buttonFocus = helper.getView<Button>(R.id.buttonFocus)

        if (item.is_follow_store) {
            buttonFocus.textSize = 14f
            buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            buttonFocus.text = Util.getString(R.string.text_focused)
            buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
        } else {
            buttonFocus.text = Util.getString(R.string.text_focus)
            buttonFocus.textSize = 13f
            buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            buttonFocus.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_focus_pavilion, 0, 0, 0)
            buttonFocus.setBackgroundResource(R.drawable.bg_color5fe4b1_radius4)
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = DesignPavilionProductAdapter(R.layout.adapter_pure_imageview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

        if (item.products == null || item.products.isEmpty()) return

        val covers = ArrayList<String>()
        for (product in item.products) {
            covers.add(product.cover)
        }
        adapter.setNewData(covers)

        helper.addOnClickListener(R.id.buttonFocus)

        //品牌馆产品点击
        adapter.setOnItemClickListener { _, _, position ->
            val productBean = item.products[position]
            PageUtil.jump2GoodsDetailActivity(productBean.rid)
        }
    }
}