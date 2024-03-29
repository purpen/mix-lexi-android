package com.lexivip.lexi.coupon
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean

class AdapterUserCoupon(list: List<MultipleItem>) : BaseMultiItemQuickAdapter<AdapterUserCoupon.MultipleItem, BaseViewHolder>(list) {
    private val pattern: String by lazy { "yyyy.MM.dd" }
    private val dp4: Int by lazy { DimenUtil.dp2px(4.0) }
    private val dp20: Int by lazy { DimenUtil.dp2px(20.0) }
    private val dp85: Int by lazy { DimenUtil.dp2px(85.0) }

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
            val imageViewCouponLeft = helper.getView<ImageView>(R.id.imageViewCouponLeft)
            val imageViewCouponRight = helper.getView<ImageView>(R.id.imageViewCouponRight)
            if (couponBean.is_expired){
                relativeLayout.setBackgroundColor(Util.getColor(R.color.color_e9e9e9))
                imageViewCouponLeft.setImageResource(R.mipmap.icon_invalid_pavilion_coupon_left)
                imageViewCouponRight.setImageResource(R.mipmap.icon_invalid_pavilion_coupon_right)
                textViewUse.setTextColor(Util.getColor(R.color.color_b8b8b8))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_invalid_coupon,0,0,0)
            }else{
                relativeLayout.setBackgroundColor(Util.getColor(android.R.color.white))
                imageViewCouponLeft.setImageResource(R.mipmap.icon_pavilion_coupon_left)
                imageViewCouponRight.setImageResource(R.mipmap.icon_valid_pavilion_coupon_right)
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_valid_bran_coupon,0,0,0)
                textViewUse.setTextColor(Util.getColor(R.color.color_ff733b))
            }
            helper.setText(R.id.textViewValue, "${couponBean.amount}")
            helper.setText(R.id.textViewUseCondition, "满${couponBean.min_amount}使用")
            val startDate = DateUtil.getDateByTimestamp(couponBean.start_at, pattern)
            val endDate = DateUtil.getDateByTimestamp(couponBean.expired_at, pattern)
            helper.setText(R.id.textViewTime, "$startDate 至 $endDate")
            helper.setText(R.id.textViewName, couponBean.store_name)
            val imageView = helper.getView<ImageView>(R.id.imageView)
            GlideUtil.loadImageWithDimenAndRadius(couponBean.store_logo, imageView, dp4, dp20,ImageSizeConfig.SIZE_AVA)
        } else { //官方
            val relativeLayout = helper.getView<RelativeLayout>(R.id.relativeLayout)
            val textViewUse = helper.getView<TextView>(R.id.textViewUse)
            val textViewTime = helper.getView<TextView>(R.id.textViewTime)
            val textViewDesc = helper.getView<TextView>(R.id.textViewDesc)
            val textViewOfficial = helper.getView<TextView>(R.id.textViewOfficial)
            val imageViewCouponLeft = helper.getView<ImageView>(R.id.imageViewCouponLeft)
            val imageViewCouponRight = helper.getView<ImageView>(R.id.imageViewCouponRight)
            if (couponBean.is_expired){
                relativeLayout.setBackgroundResource(R.drawable.bg_colore9e9e9_radius8)
                imageViewCouponLeft.setImageResource(R.mipmap.icon_invalid_official_coupon_left)
                imageViewCouponRight.setImageResource(R.mipmap.icon_invalid_official_coupon_right)
                textViewUse.setTextColor(Util.getColor(R.color.color_b8b8b8))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_invalid_coupon,0,0,0)
                textViewDesc.setTextColor(Util.getColor(R.color.color_999))
                textViewOfficial.setBackgroundResource(R.drawable.bg_colorb2b2b2_radius2)
            }else{
                relativeLayout.setBackgroundResource(R.drawable.bg_colorfff_radius8)
                imageViewCouponLeft.setImageResource(R.mipmap.icon_official_coupon_left)
                imageViewCouponRight.setImageResource(R.mipmap.icon_valid_official_coupon_right)
                textViewUse.setTextColor(Util.getColor(R.color.color_dab867))
                textViewTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sign_dab867,0,0,0)
                textViewDesc.setTextColor(Util.getColor(R.color.color_dab867))
                textViewOfficial.setBackgroundResource(R.drawable.bg_colordab867_radius2)
            }
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