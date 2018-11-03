package com.lexivip.lexi.index.lifehouse

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
import com.lexivip.lexi.R
import com.lexivip.lexi.index.selection.HeadImageAdapter
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.Util
import com.lexivip.lexi.beans.ProductBean
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan

class LifeHouseAdapter(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res){
    private val dp87:Int by lazy { DimenUtil.dp2px(87.0) }
    private val dp28:Int by lazy { DimenUtil.dp2px(28.0) }
    private val dp20:Int by lazy { DimenUtil.dp2px(20.0) }
    private val dp12:Int by lazy { DimenUtil.dp2px(12.0) }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        val textView0 = helper.getView<TextView>(R.id.textView0)
        if (item.is_free_postage){
            val drawable = Util.getDrawableWidthPxDimen(R.mipmap.icon_free_express,dp20,dp12)
            val span = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
            val spannable = SpannableString("   "+item.name)
            spannable.setSpan(span,0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            textView0.text = spannable
        }else{
            textView0.text = item.name
        }

        val textView2 = helper.getView<TextView>(R.id.textView2)

        if (item.min_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.min_price}")
            textView2.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            textView2.visibility = View.VISIBLE
            helper.setText(R.id.textView1, "￥${item.min_sale_price}")
            textView2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textView2.text = "￥" + item.min_price
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

        GlideUtil.loadCircleImageWidthDimen(item.cover,imageViewAvatar,dp28)

        val imageView = helper.getView<ImageView>(R.id.imageViewGoods)
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView,0,dp87)

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
                private val dp5=DimenUtil.dp2px(5.0)
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0) {
                        outRect.left = dp5
                    }
                }
            })
        }

//        val linearLayoutLoadMore = helper.getView<View>(R.id.linearLayoutLoadMore)

        //点击加载更多
//        helper.addOnClickListener(R.id.linearLayoutLoadMore)

//        LogUtil.e("layoutpositon==="+helper.layoutPosition+";;data.size==="+data.size)
//        if (helper.layoutPosition==data.size){
//            linearLayoutLoadMore.visibility = View.VISIBLE
//        }else{
//            linearLayoutLoadMore.visibility = View.GONE
//        }
    }
}