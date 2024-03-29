package com.lexivip.lexi.order

import android.support.annotation.LayoutRes
import android.widget.CheckBox
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean

class AdapterDialogPavilionCoupon(@LayoutRes res: Int): BaseQuickAdapter<CouponBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: CouponBean) {
        val textViewValue = helper.getView<TextView>(R.id.textViewValue)
        val textViewContent = helper.getView<TextView>(R.id.textViewContent)
        val textViewTime = helper.getView<TextView>(R.id.textViewTime)

        textViewValue.text = "${item.amount}"
        textViewContent.text = "满${item.min_amount}使用"
        textViewTime.text = "${DateUtil.getDateByTimestamp(item.start_date,DateUtil.PATTERN_DOT)}至${DateUtil.getDateByTimestamp(item.end_date,DateUtil.PATTERN_DOT)}"
        if (item.expired_at==0L || item.start_at==0L){
            textViewTime.text = "${DateUtil.getDateByTimestamp(item.start_date, DateUtil.PATTERN_DOT)}至${DateUtil.getDateByTimestamp(item.end_date, DateUtil.PATTERN_DOT)}"
        }else{
            textViewTime.text = "${DateUtil.getDateByTimestamp(item.start_at, DateUtil.PATTERN_DOT)}至${DateUtil.getDateByTimestamp(item.expired_at, DateUtil.PATTERN_DOT)}"
        }
        val checkBox = helper.getView<CheckBox>(R.id.checkBox)
        checkBox.isChecked = item.selected
    }
}