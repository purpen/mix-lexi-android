package com.lexivip.lexi.index.detail

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean

class AdapterDialogCoupon(@LayoutRes res: Int): BaseQuickAdapter<CouponBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: CouponBean) {
        val view = helper.getView<View>(R.id.relativeLayout)
        val textViewUnit = helper.getView<TextView>(R.id.textViewUnit)
        val textViewValue = helper.getView<TextView>(R.id.textViewValue)
        val textViewContent = helper.getView<TextView>(R.id.textViewContent)
        val textViewTime = helper.getView<TextView>(R.id.textViewTime)
        val textViewStatus = helper.getView<TextView>(R.id.textViewStatus)

        when(item.status){
            0 ->{//未领取
                textViewUnit.setTextColor(Util.getColor(R.color.color_ff6934))
                textViewValue.setTextColor(Util.getColor(R.color.color_ff6934))
                textViewContent.setTextColor(Util.getColor(R.color.color_6ed7af))
                textViewTime.setTextColor(Util.getColor(R.color.color_666))
                textViewStatus.text = Util.getString(R.string.text_share_get)
                view.setBackgroundResource(R.mipmap.icon_coupon_enable)
            }
            1 ->{//已领取
                textViewUnit.setTextColor(Util.getColor(R.color.color_999))
                textViewValue.setTextColor(Util.getColor(R.color.color_999))
                textViewContent.setTextColor(Util.getColor(R.color.color_999))
                textViewTime.setTextColor(Util.getColor(R.color.color_b2b2b2))
                textViewStatus.text = Util.getString(R.string.text_already_get_coupon)
                view.setBackgroundResource(R.mipmap.icon_coupon_disable)
            }
        }
        textViewValue.text = "${item.amount}"
        textViewContent.text = "满${item.min_amount}使用"
        textViewTime.text = "有效期${DateUtil.getDateByTimestamp(item.start_date,DateUtil.PATTERN_DOT)}至${DateUtil.getDateByTimestamp(item.end_date,DateUtil.PATTERN_DOT)}"
    }
}