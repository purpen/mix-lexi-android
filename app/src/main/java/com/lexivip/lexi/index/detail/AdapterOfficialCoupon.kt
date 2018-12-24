package com.lexivip.lexi.index.detail

import android.support.annotation.LayoutRes
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean

class AdapterOfficialCoupon(@LayoutRes res: Int) : BaseQuickAdapter<CouponBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: CouponBean) {
        val imageViewCouponRight = helper.getView<ImageView>(R.id.imageViewCouponRight)
        val textViewUnit = helper.getView<TextView>(R.id.textViewUnit)
        val textViewValue = helper.getView<TextView>(R.id.textViewValue)
        val textViewContent = helper.getView<TextView>(R.id.textViewContent)
        val textViewTime = helper.getView<TextView>(R.id.textViewTime)
        val textViewStatus = helper.getView<TextView>(R.id.textViewStatus)
        val textViewDesc = helper.getView<TextView>(R.id.textViewDesc)

        if (item.is_grant) {
            //已领取
            textViewUnit.setTextColor(Util.getColor(R.color.color_999))
            textViewValue.setTextColor(Util.getColor(R.color.color_999))
            textViewContent.setTextColor(Util.getColor(R.color.color_999))
            textViewDesc.setTextColor(Util.getColor(R.color.color_999))
            textViewTime.setTextColor(Util.getColor(R.color.color_b2b2b2))
            textViewStatus.text = Util.getString(R.string.text_already_get_coupon)
            imageViewCouponRight.setImageResource(R.mipmap.icon_bg_coupon_goods_detail_disable)
        } else {
            //未领取
            textViewUnit.setTextColor(Util.getColor(R.color.color_ff6934))
            textViewValue.setTextColor(Util.getColor(R.color.color_ff6934))
            textViewContent.setTextColor(Util.getColor(R.color.color_6ed7af))
            textViewDesc.setTextColor(Util.getColor(R.color.color_333))
            textViewTime.setTextColor(Util.getColor(R.color.color_666))
            textViewStatus.text = Util.getString(R.string.text_share_get)
            imageViewCouponRight.setImageResource(R.mipmap.icon_bg_coupon_goods_detail_enable)

        }

        helper.setText(R.id.textViewDesc, item.category_name)
        textViewValue.text = "${item.amount}"
        textViewContent.text = "满${item.min_amount}使用"
        textViewTime.text = "有效期${DateUtil.getDateByTimestamp(item.start_date, DateUtil.PATTERN_DOT)}至${DateUtil.getDateByTimestamp(item.end_date, DateUtil.PATTERN_DOT)}"
    }
}