package com.lexivip.lexi.index.explore.collection
import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.RecyclerViewDivider
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.explore.GoodsCollectionBean

class AdapterCollectionList(@LayoutRes res: Int) : BaseQuickAdapter<GoodsCollectionBean.DataBean.CollectionsBean, BaseViewHolder>(res) {

    private val context:Context by lazy { AppApplication.getContext() }
    private val dp10:Int by lazy { DimenUtil.dp2px(10.0) }
    private val color: Int by lazy { Util.getColor(android.R.color.transparent) }

    override fun convert(helper: BaseViewHolder, item: GoodsCollectionBean.DataBean.CollectionsBean) {

        val imageView = helper.getView<ImageView>(R.id.imageView)
        GlideUtil.loadImageWithFading(item.cover,imageView)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewGoods)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CollectionProductAdapter(R.layout.adapter_pure_imageview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.setNewData(item.products)
        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(RecyclerViewDivider(context,LinearLayoutManager.HORIZONTAL, dp10, color))

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewNum,"${item.products.size}件商品")

        //跳转商品详情
        adapter.setOnItemClickListener { _, _, position ->
            val productBean = adapter.getItem(position)
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,productBean)
            context.startActivity(intent)
        }
    }
}