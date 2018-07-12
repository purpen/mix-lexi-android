package com.thn.lexi.goods

import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import com.thn.lexi.view.CenterShareView


class GoodsAdapter(@LayoutRes res: Int) : BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder>(res), View.OnClickListener {

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

        helper.addOnClickListener(R.id.textView3)

        //购买
        helper.getView<View>(R.id.textView4).setOnClickListener(this)

        //分享
        helper.getView<View>(R.id.textView5).setOnClickListener(this)

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


    override fun onClick(v: View) {
        when (v.id) {
            R.id.textView4 -> {
                //TODO 请求商品SKU信息,设置给窗口
                val popupWindow = GoodsSpecPopupWindow(activity, R.layout.dialog_purchase_goods, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                popupWindow.show()
            }
            R.id.textView5 -> {
                val dialog = DialogPlus.newDialog(mContext)
                        .setContentHolder(ViewHolder(CenterShareView(mContext)))
                        .setGravity(Gravity.CENTER)
                        .create()
                dialog.show()
            }
        }
    }
}