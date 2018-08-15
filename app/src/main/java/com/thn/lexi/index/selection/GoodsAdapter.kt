package com.thn.lexi.index.selection

import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class GoodsAdapter(@LayoutRes res: Int) : BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder>(res){

    private var activity: FragmentActivity? = null

    constructor(@LayoutRes res: Int, activity: FragmentActivity?) : this(res) {
        this.activity = activity
    }

    override fun convert(helper: BaseViewHolder, item: GoodsData.DataBean.ProductsBean) {
        helper.setText(R.id.textView0, item.name)
        helper.setText(R.id.textView1, "${item.sale_price}")
        helper.setText(R.id.textView2, "喜欢 +329")
        val imageView = helper.getView<ImageView>(R.id.imageView)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, imageView.context.resources.getDimensionPixelSize(R.dimen.dp385))
        imageView.layoutParams = params
        GlideUtil.loadImageWithFading(item.cover, imageView)

        //喜欢
        helper.addOnClickListener(R.id.textView3)

        //购买
        helper.addOnClickListener(R.id.textView4)

        //分享
        helper.addOnClickListener(R.id.textView5)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(imageView.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        var urlList = ArrayList<String>()
        urlList.add(item.cover)
        headImageAdapter.setNewData(urlList)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = headImageAdapter

        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) > 0) {
                        outRect.left = parent.context.resources.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

    }
}