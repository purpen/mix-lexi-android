package com.lexivip.lexi.coupon

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean

class AdapterUserCoupon(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterUserCoupon.MultipleItem, BaseViewHolder>(list) {
//    private val sizeScreen: Int by lazy { ScreenUtil.getScreenWidth() - DimenUtil.dp2px(30.0) }
    private val pattern: String by lazy { "yyyy.MM.dd" }
    private val layoutParams: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT) }
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val dp20: Int by lazy { DimenUtil.dp2px(20.0) }

    init {
        addItemType(MultipleItem.ITEM_TYPE_BRAND, R.layout.adapter_user_brand_coupon)
        addItemType(MultipleItem.ITEM_TYPE_LX, R.layout.adapter_user_official_coupon)
    }

    class MultipleItem(var coupon: CouponBean, private var itemType: Int) : MultiItemEntity {
        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val ITEM_TYPE_BRAND = 0x0010
            const val ITEM_TYPE_LX = 0x0011
        }
    }


    override fun convert(helper: BaseViewHolder, item: AdapterUserCoupon.MultipleItem) {
        val couponBean = item.coupon
        if (item.itemType == MultipleItem.ITEM_TYPE_BRAND) { //品牌馆
            val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)
            val textViewUse = helper.getView<TextView>(R.id.textViewUse)
            val textViewTime = helper.getView<TextView>(R.id.textViewTime)
            if (couponBean.is_expired){
                relativeLayout.setBackgroundResource(R.mipmap.icon_user_brand_invalid_coupon)
                textViewUse.setTextColor(Util.getColor(R.color.color_b8b8b8))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_invalid_coupon,0,0,0)
            }else{
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_valid_bran_coupon,0,0,0)
                relativeLayout.setBackgroundResource(R.mipmap.icon_user_brand_valid_coupon)
                textViewUse.setTextColor(Util.getColor(R.color.color_ff733b))
            }
            relativeLayout.setPadding(0,0,0,0)
            helper.setText(R.id.textViewValue, "${couponBean.amount}")
            helper.setText(R.id.textViewUseCondition, "满${couponBean.min_amount}使用")
            val startDate = DateUtil.getDateByTimestamp(couponBean.start_at, pattern)
            val endDate = DateUtil.getDateByTimestamp(couponBean.expired_at, pattern)
            helper.setText(R.id.textViewTime, "$startDate 至 $endDate")
            helper.setText(R.id.textViewName, couponBean.store_name)
            val imageView = helper.getView<ImageView>(R.id.imageView)
            GlideUtil.loadImageWithDimenAndRadius(couponBean.store_logo, imageView, dp4, dp20)
        } else { //官方
            val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)
            val textViewUse = helper.getView<TextView>(R.id.textViewUse)
            val textViewTime = helper.getView<TextView>(R.id.textViewTime)
            val textViewDesc = helper.getView<TextView>(R.id.textViewDesc)
            val textViewOfficial = helper.getView<TextView>(R.id.textViewOfficial)
            if (couponBean.is_expired){
                relativeLayout.setBackgroundResource(R.mipmap.icon_user_lx_invalid_coupon)
                textViewUse.setTextColor(Util.getColor(R.color.color_b8b8b8))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_invalid_coupon,0,0,0)
                textViewDesc.setTextColor(Util.getColor(R.color.color_999))
                textViewOfficial.setBackgroundResource(R.drawable.bg_colorb2b2b2_radius2)
            }else{
                relativeLayout.setBackgroundResource(R.mipmap.icon_user_lx_valid_coupon)
                textViewUse.setTextColor(Util.getColor(R.color.color_dab867))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_dab867,0,0,0)
                textViewDesc.setTextColor(Util.getColor(R.color.color_dab867))
                textViewOfficial.setBackgroundResource(R.drawable.bg_colordab867_radius2)
            }
            relativeLayout.setPadding(0,0,0,0)
            helper.setText(R.id.textViewUseCondition, "满${couponBean.min_amount}使用")
            helper.setText(R.id.textViewValue, "${couponBean.amount}")
            val startDate = DateUtil.getDateByTimestamp(couponBean.start_at, pattern)
            val endDate = DateUtil.getDateByTimestamp(couponBean.expired_at, pattern)
            helper.setText(R.id.textViewTime, "$startDate 至 $endDate")
            //1、分享领红包 2、猜图赢现金 3、赠送 4、新人奖励 11、领券中心 12、店铺
            var sourceStr = ""
            when (couponBean.source) {
                1 -> {
                    sourceStr = "分享领红包"
                }
                2 -> {
                    sourceStr = "猜图赢现金"
                }
                3 -> {
                    sourceStr = "赠送"
                }

                4 -> {
                    sourceStr = "新人奖励"
                }

                11 -> {
                    sourceStr = "领券中心"
                }
            }

            helper.setText(R.id.textViewType, sourceStr)
            helper.setText(R.id.textViewDesc,couponBean.category_name )

        }


    }
}