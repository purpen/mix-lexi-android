package com.thn.lexi.goods.lifehouse
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean

class WelcomeInWeekAdapter(layoutResId: Int) : BaseQuickAdapter<EditorRecommendBean.DataBean.ProductsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: EditorRecommendBean.DataBean.ProductsBean) {

        val relativeLayout = helper.getView<View>(R.id.relativeLayout)
        val pixelSize = DimenUtil.getDimensionPixelSize(R.dimen.dp155)
        var layoutParams = ViewGroup.LayoutParams(pixelSize,ViewGroup.LayoutParams.WRAP_CONTENT)
        relativeLayout.layoutParams = layoutParams

        val imageViewStatus = helper.getView<View>(R.id.imageViewStatus)
        if (item.is_sold_out){
            imageViewStatus.visibility = View.VISIBLE
        }else{
            imageViewStatus.visibility = View.GONE
        }

        val imageViewExpress = helper.getView<View>(R.id.imageViewExpress)

        if (item.is_free_postage){
            imageViewExpress.visibility = View.VISIBLE
        }else{
            imageViewExpress.visibility = View.GONE
        }

        val textViewLike = helper.getView<TextView>(R.id.textViewLike)
        textViewLike.visibility = View.VISIBLE
        textViewLike.text = "喜欢 +${item.like_count}"

        helper.setText(R.id.textViewTitle,item.name)

//        val textViewOldPrice =helper.getView<TextView>(R.id.textViewOldPrice)

        if (item.real_sale_price ==0.0){ //折扣价为0,显示真实价格
            helper.setText(R.id.textViewPrice, "${item.real_price}")
//            textViewOldPrice.visibility = View.GONE
        }else{ //折扣价不为0显示折扣价格和带划线的真实价格
//            textViewOldPrice.visibility = View.VISIBLE
            helper.setText(R.id.textViewPrice, "${item.real_sale_price}")
//            textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
//            textViewOldPrice.text = "￥" + item.real_price
        }

        val imageView = helper.getView<ImageView>(R.id.imageView)
        imageView.layoutParams = RelativeLayout.LayoutParams(pixelSize,pixelSize)
        GlideUtil.loadImageWithRadius(item.cover,imageView,DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }
}