package com.thn.lexi.goods.lifehouse

import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.selection.HeadImageAdapter
import android.widget.TextView


class LifeHouseAdapter(@LayoutRes res: Int) : BaseQuickAdapter<DistributionGoodsBean.DataBean.ProductsBean, BaseViewHolder>(res){
    override fun convert(helper: BaseViewHolder, item: DistributionGoodsBean.DataBean.ProductsBean) {

        helper.setText(R.id.textView0, item.name)
        helper.setText(R.id.textView1, "￥${item.real_sale_price}")
        val textView2 = helper.getView<TextView>(R.id.textView2)
        textView2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
        textView2.text = "￥${item.min_sale_price}"
        helper.setText(R.id.textView3, "喜欢 +329")

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)

        GlideUtil.loadCircleImageWidthDimen(item.cover,imageViewAvatar,imageViewAvatar.resources.getDimensionPixelSize(R.dimen.dp28))

        val imageView = helper.getView<ImageView>(R.id.imageViewGoods)
        GlideUtil.loadImageWithFading(item.cover, imageView)

        //删除分销商品
        helper.addOnClickListener(R.id.imageViewDelete)

        //喜欢
        helper.addOnClickListener(R.id.textView4)

        //分享
        helper.addOnClickListener(R.id.textView5)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewHeader)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(imageView.context,LinearLayoutManager.HORIZONTAL,false)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        val urlList = ArrayList<String>()
        urlList.add(item.cover)
        urlList.add(item.cover)
        urlList.add(item.cover)
        headImageAdapter.setNewData(urlList)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = headImageAdapter

        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0) {
                        outRect.left = parent.context.resources.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

    }
}