package com.thn.lexi.goods.lifehouse

import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.selection.HeadImageAdapter
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.Util


class LifeHouseAdapter(@LayoutRes res: Int) : BaseQuickAdapter<DistributionGoodsBean.DataBean.ProductsBean, BaseViewHolder>(res){
    override fun convert(helper: BaseViewHolder, item: DistributionGoodsBean.DataBean.ProductsBean) {
        helper.setText(R.id.textView0, item.name)

        val textView2 = helper.getView<TextView>(R.id.textView2)

        if (item.real_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.real_price}")
            textView2.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            textView2.visibility = View.VISIBLE
            helper.setText(R.id.textView1, "￥${item.real_sale_price}")
            textView2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textView2.text = "￥" + item.real_price
        }

        helper.setText(R.id.textView3, "喜欢 +${item.like_count}")

        val textViewDesc = helper.getView<TextView>(R.id.textViewDesc)
        if (TextUtils.isEmpty(item.stick_text)){
            textViewDesc.visibility = View.GONE
        }else{
            textViewDesc.visibility = View.VISIBLE
            textViewDesc.text = item.stick_text
        }


        val textView4 = helper.getView<TextView>(R.id.textView4)

        if (item.is_like){
            textView4.setTextColor(Util.getColor(R.color.color_6ed7af))
            textView4.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_selected,0,0,0)
        }else{
            textView4.setTextColor(Util.getColor(R.color.color_959fa7))
            textView4.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_normal,0,0,0)
        }

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)

        GlideUtil.loadCircleImageWidthDimen(item.cover,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp28))

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
        val urlList = ArrayList<String>(3)

        for (userItem in item.product_like_users){
            urlList.add(userItem.avatar)
            if (urlList.size==3) break
        }

        headImageAdapter.setNewData(urlList)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = headImageAdapter

        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0) {
                        outRect.left = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

        val linearLayoutLoadMore = helper.getView<View>(R.id.linearLayoutLoadMore)

        //点击加载更多
        helper.addOnClickListener(R.id.linearLayoutLoadMore)

//        LogUtil.e("layoutpositon==="+helper.layoutPosition+";;data.size==="+data.size)
        if (helper.layoutPosition==data.size){
            linearLayoutLoadMore.visibility = View.VISIBLE
        }else{
            linearLayoutLoadMore.visibility = View.GONE
        }
    }
}