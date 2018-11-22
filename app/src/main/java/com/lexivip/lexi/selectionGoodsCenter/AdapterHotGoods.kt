package com.lexivip.lexi.selectionGoodsCenter
import android.graphics.Paint
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean

class AdapterHotGoods(@LayoutRes res: Int) : BaseQuickAdapter<ProductBean, BaseViewHolder>(res) {
    private val dp100:Int by lazy { DimenUtil.dp2px(100.0) }
    private val dp80:Int by lazy { DimenUtil.dp2px(80.0) }
    private val dp60:Int by lazy { DimenUtil.dp2px(60.0) }
    private val dp25:Int by lazy { DimenUtil.dp2px(25.0) }
    private val dp15:Int by lazy { DimenUtil.dp2px(15.0) }
    private val layoutParams:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(RelativeLayout.LayoutParams(dp80,dp25)) }
    private val layoutParams1:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(RelativeLayout.LayoutParams(dp60,dp25)) }
    private val color_949ea6:Int by lazy { Util.getColor(R.color.color_949ea6)}
    private val white:Int by lazy { Util.getColor(android.R.color.white)}

    private val size4 = DimenUtil.getDimensionPixelSize(R.dimen.dp4)
    override fun convert(helper: BaseViewHolder, item: ProductBean) {
        helper.setText(R.id.textView0, item.name)

        val textView2 = helper.getView<TextView>(R.id.textView2)

        if (item.real_sale_price == 0.0) { //折扣价为0,显示真实价格
            helper.setText(R.id.textView1, "￥${item.real_price}")
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            helper.setText(R.id.textView1, "￥${item.real_sale_price}")
            textView2.visibility = View.VISIBLE
            textView2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textView2.text = "￥${item.real_price}"
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
        GlideUtil.loadImageWithDimenAndRadius(item.cover, imageView, size4,dp100,dp100,ImageSizeConfig.SIZE_P30X2)



        //卖
        helper.addOnClickListener(R.id.textView4)

        val textView4 = helper.getView<TextView>(R.id.textView4)
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_END)
        layoutParams1.leftMargin = dp15
        layoutParams1.addRule(RelativeLayout.CENTER_VERTICAL)
        textView4.layoutParams = layoutParams1

        val textView5 = helper.getView<TextView>(R.id.textView5)
        layoutParams.addRule(RelativeLayout.START_OF,R.id.textView4)
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        textView5.layoutParams = layoutParams

        if (item.have_distributed){
            textView5.isEnabled = false
            textView5.text = Util.getString(R.string.text_already_putaway)
            textView5.setTextColor(color_949ea6)
            textView5.setBackgroundResource(R.drawable.bg_radius_round_eff3f2)
        }else{
            textView5.isEnabled = true
            textView5.text = Util.getString(R.string.text_putaway)
            textView5.setTextColor(white)
            textView5.setBackgroundResource(R.drawable.bg_radius_round_2d343a)
        }
        //上架
        helper.addOnClickListener(R.id.textView5)

    }
}