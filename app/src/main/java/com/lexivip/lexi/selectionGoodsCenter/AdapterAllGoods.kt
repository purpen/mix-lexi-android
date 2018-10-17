package com.lexivip.lexi.selectionGoodsCenter

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterAllGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {

    private var imageViewWidth: Int = ((ScreenUtil.getScreenWidth() - DimenUtil.getDimensionPixelSize(R.dimen.dp50)) * 0.5).toInt()
    private val color_949ea6: Int by lazy { Util.getColor(R.color.color_949ea6) }
    private val white: Int by lazy { Util.getColor(android.R.color.white) }
    private val dp50:Int by lazy { DimenUtil.dp2px(50.0) }
    private val dp20:Int by lazy { DimenUtil.dp2px(20.0) }
    private val dp15:Int by lazy { DimenUtil.dp2px(15.0) }
    private val layoutParams:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(RelativeLayout.LayoutParams(dp50,dp20)) }
    private val layoutParams1:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(RelativeLayout.LayoutParams(dp50,dp20)) }
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.setText(R.id.textView0, item.name)

        if (item.real_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.real_price}")
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            helper.setText(R.id.textView1, "￥${item.real_sale_price}")
        }

        helper.setText(R.id.textView3, "喜欢 +${item.like_count}")

        helper.setText(R.id.textViewEarn, "赚 ￥${item.commission_price}")

        val imageViewStatus = helper.getView<ImageView>(R.id.imageViewStatus)
        if (item.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        val imageViewFreeExpress = helper.getView<ImageView>(R.id.imageViewFreeExpress)
        if (item.is_free_postage) {
            imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            imageViewFreeExpress.visibility = View.VISIBLE
        }

        val imageView = helper.getView<ImageView>(R.id.imageViewGoods)

        imageView.layoutParams = RelativeLayout.LayoutParams(imageViewWidth, imageViewWidth)

        GlideUtil.loadImageWithRadius(item.cover, imageView, DimenUtil.getDimensionPixelSize(R.dimen.dp4))


        //卖
        helper.addOnClickListener(R.id.textView4)
        val textView4 = helper.getView<TextView>(R.id.textView4)
        layoutParams.addRule(RelativeLayout.END_OF,R.id.textView5)
        layoutParams.leftMargin = dp15
        textView4.layoutParams = layoutParams
        //上架
        helper.addOnClickListener(R.id.textView5)
        val textView5 = helper.getView<TextView>(R.id.textView5)
        textView5.layoutParams = layoutParams1

        if (item.have_distributed) {
            textView5.isEnabled = false
            textView5.text = Util.getString(R.string.text_already_putaway)
            textView5.setTextColor(color_949ea6)
            textView5.setBackgroundResource(R.drawable.bg_radius_round_eff3f2)
        } else {
            textView5.isEnabled = true
            textView5.text = Util.getString(R.string.text_putaway)
            textView5.setTextColor(white)
            textView5.setBackgroundResource(R.drawable.bg_radius_round_2d343a)
        }
    }
}