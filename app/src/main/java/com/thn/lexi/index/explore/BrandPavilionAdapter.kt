package com.thn.lexi.index.explore

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class BrandPavilionAdapter(layoutResId: Int) : BaseQuickAdapter<BrandPavilionListBean.DataBean.StoresBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: BrandPavilionListBean.DataBean.StoresBean) {
        val imageViewBg = helper.getView<ImageView>(R.id.imageViewBg)
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithBlurAndRadius(item.bgcover,imageViewBg,size)

        val imageViewShop = helper.getView<ImageView>(R.id.imageViewShop)
        GlideUtil.loadImageWithRadius(item.logo,imageViewShop,size)

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewCount,"${item.store_products_counts}件")

        if (item.is_followed){
            helper.setText(R.id.buttonFocus,Util.getString(R.string.text_focused))
        }else{
            helper.setText(R.id.buttonFocus,Util.getString(R.string.text_focus))
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL,false)
        val adapter = BrandPavilionProductAdapter(R.layout.adapter_brand_pavilion_product)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.setNewData(item.products_cover)
        helper.addOnClickListener(R.id.imageViewShop)

        helper.addOnClickListener(R.id.buttonFocus)
    }
}