package com.lexivip.lexi.brandPavilion

import android.content.Context
import android.content.Intent
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
import com.lexivip.lexi.index.explore.BrandPavilionListBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendAdapter

class AdapterFeatureBrandPavilion(layoutResId: Int) : BaseQuickAdapter<BrandPavilionListBean.DataBean.StoresBean, BaseViewHolder>(layoutResId) {
    private val size10: Int by lazy { DimenUtil.getDimensionPixelSize(R.dimen.dp10) }
    private val size60: Int by lazy { DimenUtil.dp2px(60.0) }
    private val size4: Int by lazy { DimenUtil.getDimensionPixelSize(R.dimen.dp4) }
    private val color: Int by lazy { Util.getColor(android.R.color.transparent) }
    private val context: Context by lazy { AppApplication.getContext() }

    override fun convert(helper: BaseViewHolder, item: BrandPavilionListBean.DataBean.StoresBean) {
        val imageViewLogo = helper.getView<ImageView>(R.id.imageViewLogo)
        GlideUtil.loadImageWithDimenAndRadius(item.logo, imageViewLogo, size4,size60)
        helper.setText(R.id.textViewName, item.name)
        helper.setText(R.id.textViewAddress, item.country + item.province + item.city)
        helper.setText(R.id.textViewDesc, item.tag_line)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(context)
        val adapter = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.setNewData(item.products)
        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(RecyclerViewDivider(context, LinearLayoutManager.HORIZONTAL, size10, color))
        //设置产品数据

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