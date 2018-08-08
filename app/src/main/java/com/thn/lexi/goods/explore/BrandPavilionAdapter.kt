package com.thn.lexi.goods.explore

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class BrandPavilionAdapter(layoutResId: Int) : BaseQuickAdapter<BrandPavilionBean.DataBean.StoresBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: BrandPavilionBean.DataBean.StoresBean) {
        val imageViewBg = helper.getView<ImageView>(R.id.imageViewBg)
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
        GlideUtil.loadImageWithBlurAndRadius(item.bgcover,imageViewBg,size)

        val imageViewShop = helper.getView<ImageView>(R.id.imageViewShop)
        GlideUtil.loadImageWithRadius(item.logo,imageViewShop,size)

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewCount,"${item.store_products_counts}件")

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL,false)
        val adapter = BrandPavilionProductAdapter(R.layout.adapter_brand_pavilion_product)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.setNewData(item.products_cover)

//        val imageViewGoods0 = helper.getView<ImageView>(R.id.imageViewGoods0)
//        GlideUtil.loadImage(item.products_cover[0],imageViewGoods0)
//
//        val imageViewGoods1 = helper.getView<ImageView>(R.id.imageViewGoods0)
//        GlideUtil.loadImage(R.mipmap.ic_launcher,imageViewGoods1)
//
//        val imageViewGoods2 = helper.getView<ImageView>(R.id.imageViewGoods0)
//        GlideUtil.loadImage(R.mipmap.ic_launcher,imageViewGoods2)

        helper.addOnClickListener(R.id.imageViewShop)

//        helper.addOnClickListener(R.id.imageViewGoods0)
//        helper.addOnClickListener(R.id.imageViewGoods1)
//        helper.addOnClickListener(R.id.imageViewGoods2)

        helper.addOnClickListener(R.id.buttonFocus)
    }
}