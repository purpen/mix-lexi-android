package com.thn.lexi.order

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.beans.CouponBean

class AdapterDialogPavilionCoupon(@LayoutRes res: Int): BaseQuickAdapter<CouponBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: CouponBean) {
//        val view = helper.getView<View>(R.id.relativeLayout)
//        val textViewUnit = helper.getView<TextView>(R.id.textViewUnit)
        val textViewValue = helper.getView<TextView>(R.id.textViewValue)
        val textViewContent = helper.getView<TextView>(R.id.textViewContent)
        val textViewTime = helper.getView<TextView>(R.id.textViewTime)

//        when(item.status){
//            0 ->{//未领取
//                textViewUnit.setTextColor(Util.getColor(R.color.color_ff6934))
//                textViewValue.setTextColor(Util.getColor(R.color.color_ff6934))
//                textViewContent.setTextColor(Util.getColor(R.color.color_6ed7af))
//                textViewTime.setTextColor(Util.getColor(R.color.color_666))
//            }
//            1 ->{//已领取
//                textViewUnit.setTextColor(Util.getColor(R.color.color_999))
//                textViewValue.setTextColor(Util.getColor(R.color.color_999))
//                textViewContent.setTextColor(Util.getColor(R.color.color_999))
//                textViewTime.setTextColor(Util.getColor(R.color.color_b2b2b2))
//            }
//        }
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