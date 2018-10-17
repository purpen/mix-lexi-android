package com.lexivip.lexi.selectionGoodsCenter

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterAllGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {

    private var imageViewWidth:Int = ((ScreenUtil.getScreenWidth()- DimenUtil.getDimensionPixelSize(R.dimen.dp50))*0.5).toInt()

    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.setText(R.id.textView0, item.name)

//        val textView2 = helper.getView<TextView>(R.id.textView2)

        if (item.real_sale_price==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.real_price}")
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
            helper.setText(R.id.textView1, "￥${item.real_sale_price}")
        }

        helper.setText(R.id.textView3, "喜欢 +${item.like_count}")

        helper.setText(R.id.textViewEarn, "赚 ￥${item.commission_price}")

        val imageViewStatus = helper.getView<ImageView>(R.id.imageViewStatus)
        if (item.is_sold_out){
            imageViewStatus.visibility = View.VISIBLE
        }else{
            imageViewStatus.visibility = View.GONE
        }

        val imageViewFreeExpress = helper.getView<ImageView>(R.id.imageViewFreeExpress)
        if (item.is_free_postage){
            imageViewFreeExpress.visibility = View.VISIBLE
        }else{
            imageViewFreeExpress.visibility = View.VISIBLE
        }

//        val textView4 = helper.getView<TextView>(R.id.textView4)

        val imageView = helper.getView<ImageView>(R.id.imageViewGoods)

        imageView.layoutParams = RelativeLayout.LayoutParams(imageViewWidth,imageViewWidth)

        GlideUtil.loadImageWithRadius(item.cover, imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4))


        //卖
        helper.addOnClickListener(R.id.textView4)

        //上架
        helper.addOnClickListener(R.id.textView5)


//        val linearLayoutLoadMore = helper.getView<View>(R.id.linearLayoutLoadMore)

//        helper.setText(R.id.textViewLoadMoreTips,R.string.text_look_more_goods)



//        LogUtil.e("layoutpositon==="+helper.layoutPosition+";;data.size==="+data.size)
//        if (helper.layoutPosition==data.size-1){
//            linearLayoutLoadMore.visibility = View.VISIBLE
//            helper.addOnClickListener(R.id.linearLayoutLoadMore)
//        }else{
//            linearLayoutLoadMore.visibility = View.GONE
//        }


    }
}